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
});