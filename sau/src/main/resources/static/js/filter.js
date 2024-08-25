$(document).ready(function () {

    $('#hospitalFilter').change(() => {
        var selectedHospital = $(this).val().toLowerCase();
        $('#doctorTable tr').filter(() => {
            $(this).toggle($(this).find('td:eq(3)')
                .text()
                .toLowerCase()
                .indexOf(selectedHospital) > -1);
        });
    });

    $('#specializationFilter').change(() => {
        var selectedSpecialization = $(this).val().toLowerCase();
        $('#doctorTable tr').filter(() => {
            $(this).toggle($(this).find('td:eq(2)')
                .text()
                .toLowerCase()
                .indexOf(selectedSpecialization) > -1);
        });
    });

});