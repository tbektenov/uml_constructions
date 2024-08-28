$(document).ready(() => {

    /**
     * Applies filters to the doctor table based on the selected hospital and specialization.
     */
    applyFilters = () => {
        var selectedHospital = $('#hospitalFilter').val().toLowerCase();
        var selectedSpecialization = $('#specializationFilter').val().toLowerCase();

        $('#doctorTable tr').each(function () {
            var hospitalMatch = $(this).find('td:eq(3)')
                .text()
                .toLowerCase()
                .indexOf(selectedHospital) > -1 || selectedHospital === "";

            var specializationMatch = $(this).find('td:eq(2)')
                .text()
                .toLowerCase()
                .indexOf(selectedSpecialization) > -1 || selectedSpecialization === "";

            $(this).toggle(hospitalMatch && specializationMatch);
        });
    }

    $('#hospitalFilter').change(applyFilters);
    $('#specializationFilter').change(applyFilters);
});