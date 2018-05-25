import 'jquery';
import 'popper.js';
import 'bootstrap';
import 'particles.js';

declare var particlesJS: any;

particlesJS.load('particles', '/static/particles.json');

$(() => {
    $('#discordModal').on('show.bs.modal hidden.bs.modal', () => {
        $('.panel').toggle();
    });
    $('.is-invalid').on('change keyup paste', function() {
        $(this).removeClass("is-invalid");
    });
});