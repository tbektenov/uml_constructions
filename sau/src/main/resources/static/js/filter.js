$(document).ready(() => {

    /**
     * Applies filters to the doctor table based on the selected hospital and specialization.
     */
    let applyFilters = () => {
        var selectedHospital = $('#hospitalFilter')
                                    .val()
                                    .toLowerCase()
                                    .trim();
        var selectedSpecialization = $('#specFilter')
                                        .val()
                                        .toLowerCase()
                                        .trim();

        $('#doctorTable tr').each(function () {
            var hospitalText = $(this).find('td:eq(3)')
                                        .text()
                                        .toLowerCase()
                                        .trim();

            var specializationText = $(this).find('td:eq(2)')
                                                .text()
                                                .toLowerCase()
                                                .trim();

            var hospitalMatch = selectedHospital === "" || hospitalText === selectedHospital;
            var specializationMatch = selectedSpecialization === "" || specializationText === selectedSpecialization;

            $(this).toggle(hospitalMatch && specializationMatch);
        });
    }

    $('#hospitalFilter').change(applyFilters);
    $('#specFilter').change(applyFilters);
});
