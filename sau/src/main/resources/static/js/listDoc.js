document.addEventListener('DOMContentLoaded', function () {
    const data = [
        { id: 1, doctor: "John Doe", specialization: 'Ophthalmologist', hospital: "XYZ" },
        { id: 2, doctor: "Jane Smith", specialization: 'Cardiologist', hospital: "ABC" },
        { id: 3, doctor: "Alice Johnson", specialization: 'Dermatologist', hospital: "LMN" },
        { id: 4, doctor: "Michael Brown", specialization: 'Neurologist', hospital: "OPQ" },
        { id: 5, doctor: "Emma Davis", specialization: 'Pediatrician', hospital: "XYZ" },
        { id: 6, doctor: "William Wilson", specialization: 'Orthopedic', hospital: "ABC" },
        { id: 7, doctor: "Olivia Martinez", specialization: 'Gynecologist', hospital: "LMN" },
        { id: 8, doctor: "James Anderson", specialization: 'Oncologist', hospital: "OPQ" },
        { id: 9, doctor: "Sophia Thomas", specialization: 'Psychiatrist', hospital: "XYZ" },
        { id: 10, doctor: "Benjamin Lee", specialization: 'Urologist', hospital: "ABC" },
    ];

    const itemsPerPage = 5;
    let currentPage = 1;

    function renderDoctors(page) {
        const doctorTableBody = document.getElementById('doctorTableBody');
        const pagination = document.getElementById('pagination');

        const indexOfLastItem = page * itemsPerPage;
        const indexOfFirstItem = indexOfLastItem - itemsPerPage;
        const currentItems = data.slice(indexOfFirstItem, indexOfLastItem);

        doctorTableBody.innerHTML = '';

        currentItems.forEach(row => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${row.doctor}</td>
                <td>${row.specialization}</td>
                <td>${row.hospital}</td>
                <td>
                    <button class="chooseButton">
                        Choose
                    </button>
                </td>
            `;
            // Добавляем обработчик события на каждую кнопку "Choose"
            const chooseButton = tr.querySelector('.chooseButton');
            chooseButton.addEventListener('click', () => handleChoose(row.doctor));
            doctorTableBody.appendChild(tr);
        });

        pagination.innerHTML = '';
        pagination.appendChild(renderPageNumbers(page));
    }

    function renderPageNumbers(currentPage) {
        const totalPages = Math.ceil(data.length / itemsPerPage);
        const maxPagesToShow = 5;
        let startPage = Math.max(1, currentPage - Math.floor(maxPagesToShow / 2));
        let endPage = Math.min(totalPages, startPage + maxPagesToShow - 1);

        if (totalPages <= maxPagesToShow) {
            startPage = 1;
            endPage = totalPages;
        } else {
            if (currentPage <= Math.ceil(maxPagesToShow / 2)) {
                endPage = maxPagesToShow;
            } else if (currentPage + Math.floor(maxPagesToShow / 2) >= totalPages) {
                startPage = totalPages - maxPagesToShow + 1;
            }
        }

        const pageNumbers = [];

        if (currentPage > 1) {
            pageNumbers.push(renderPageButton('prev', 'Previous', currentPage - 1));
        }

        for (let i = startPage; i <= endPage; i++) {
            pageNumbers.push(renderPageButton(i, i, i));
        }

        if (currentPage < totalPages) {
            pageNumbers.push(renderPageButton('next', 'Next', currentPage + 1));
        }

        return pageNumbers;
    }

    function renderPageButton(key, text, pageNumber) {
        const button = document.createElement('button');
        button.classList.add('pageNumber');
        if (key === 'prev' || key === 'next') {
            button.textContent = text;
        } else {
            button.textContent = text;
            button.classList.add(currentPage === pageNumber ? 'active' : '');
            button.addEventListener('click', () => handlePageChange(pageNumber));
        }
        return button;
    }

    function handlePageChange(pageNumber) {
        currentPage = pageNumber;
        renderDoctors(currentPage);
    }

    function handleChoose(doctor) {
        alert(`You have chosen Dr. ${doctor}`);
    }

    // Initial render
    renderDoctors(currentPage);
});
