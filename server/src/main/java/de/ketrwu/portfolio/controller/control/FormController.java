package de.ketrwu.portfolio.controller.control;

import de.ketrwu.portfolio.forms.CaptchaForm;
import de.ketrwu.portfolio.forms.Form;
import de.ketrwu.portfolio.forms.validation.formtoken.FormToken;
import de.ketrwu.portfolio.service.CaptchaService;
import de.ketrwu.portfolio.service.FormTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public abstract class FormController<T extends Form> {

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private FormTokenService formTokenService;

    @GetMapping
    public String getForm(@ModelAttribute("form") T form, Map<String, Object> model) throws IllegalAccessException, IOException, InstantiationException {
        form = resetForm(form);
        model.put("form", form);
        return getTemplate();
    }

    @PostMapping
    public String submitForm(@ModelAttribute("form") @Validated T form, BindingResult bindingResult, Map<String, Object> model) throws IllegalAccessException, InstantiationException, IOException {
        if (!bindingResult.hasErrors()) {
            onSuccess(form);
            formTokenService.invalidateFormToken(form);
            form = resetForm(form);
        } else if (form instanceof CaptchaForm) {
            captchaService.createCaptcha((CaptchaForm) form);
        }

        if (bindingResult.hasErrors()) {
            onError(form, bindingResult);
        }

        List<String> errors = new ArrayList<>();
        for (ObjectError error : bindingResult.getAllErrors()) {
            if (error.getCode().equals(FormToken.class.getSimpleName())) {
                onResubmit(form);
                errors.clear();
                errors.add(error.getDefaultMessage());
                formTokenService.invalidateFormToken(form);
                form = resetForm(form);
                break;
            }
            errors.add(error.getDefaultMessage());
        }

        Collections.reverse(errors);

        model.put("success", !bindingResult.hasErrors());
        model.put("error", bindingResult.hasErrors());
        model.put("errors", errors);
        model.put("form", form);

        return getTemplate();
    }

    private T resetForm(T form) throws IOException, IllegalAccessException, InstantiationException {
        form = (T) form.getClass().newInstance();
        formTokenService.tokenize(form);
        if (form instanceof CaptchaForm) {
            captchaService.createCaptcha((CaptchaForm) form);
        }
        return form;
    }

    public abstract String getTemplate();

    public void onSuccess(T form) {}

    public void onResubmit(T form) {}

    public void onError(T form, BindingResult bindingResult) {}

}
