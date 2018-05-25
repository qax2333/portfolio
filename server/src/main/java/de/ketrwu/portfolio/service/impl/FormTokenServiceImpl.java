package de.ketrwu.portfolio.service.impl;

import de.ketrwu.portfolio.forms.Form;
import de.ketrwu.portfolio.service.FormTokenService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FormTokenServiceImpl implements FormTokenService {

    private List<String> tokens = new ArrayList<>();

    @Override
    public void tokenize(Form form) {
        form.setFormToken(UUID.randomUUID().toString());
        tokens.add(form.getFormToken());
    }

    @Override
    public boolean isFormTokenValid(Form form) {
        if (form.getFormToken() == null) {
            return false;
        }
        return tokens.contains(form.getFormToken());
    }

    @Override
    public void invalidateFormToken(Form form) {
        if (isFormTokenValid(form)) {
            tokens.remove(form.getFormToken());
            form.setFormToken(null);
        }
    }

}
