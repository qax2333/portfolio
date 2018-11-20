import 'jquery';
import 'popper.js';
import 'bootstrap';
import * as Parallax from 'parallax-js/dist/parallax.min';

$(() => {
    new Parallax($('#parallax').get(0), {
        relativeInput: true
    });
    $('#discordModal').on('show.bs.modal hidden.bs.modal', () => {
        $('.panel').toggle();
    });
    $('.is-invalid').on('change keyup paste', function() {
        $(this).removeClass("is-invalid");
    });
    $('.card').click(function() {
        window.location.href = $(this).find("a.card-link").attr("href").toString();
    });
    $('#btn-captcha-reload').click(function() {
        $(this).attr("disabled", "disabled");
        $.ajax({
            type: 'POST',
            beforeSend: function(request) {
                request.setRequestHeader("Content-Type", "application/json");
            },
            url: "/api/captcha",
            data: JSON.stringify({
                "token": $("input[name='captchaToken']").val()
            }),
            dataType: "json",
            success: function(data) {
                $("input[name='captchaToken']").val(data.token);
                $(".captcha__img").attr("src", "data:image/png;base64, " + data.image);
                $("input[name='captchaResponse']").val("");
                $('#btn-captcha-reload').removeAttr("disabled");
            }
        });
    });
});