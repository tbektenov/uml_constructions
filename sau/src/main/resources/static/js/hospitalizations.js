document.addEventListener("DOMContentLoaded", function () {
    let data = [
        { id: 1, location: "Hospital 1, address 1", start: "01.01.2024", end: "10.01.2024", status: "Ongoing" },
        { id: 2, location: "Hospital 2, address 2", start: "02.01.2024", end: "11.01.2024", status: "Archived" },
        { id: 3, location: "Hospital 3, address 3", start: "03.01.2024", end: "12.01.2024", status: "Ongoing" },
        { id: 4, location: "Hospital 4, address 4", start: "04.01.2024", end: "13.01.2024", status: "Archived" },
        { id: 5, location: "Hospital 5, address 5", start: "05.01.2024", end: "14.01.2024", status: "Ongoing" },
        { id: 6, location: "Hospital 6, address 6", start: "06.01.2024", end: "15.01.2024", status: "Archived" },
        { id: 7, location: "Hospital 7, address 7", start: "07.01.2024", end: "16.01.2024", status: "Ongoing" },
        { id: 8, location: "Hospital 8, address 8", start: "08.01.2024", end: "17.01.2024", status: "Archived" },
        { id: 9, location: "Hospital 9, address 9", start: "09.01.2024", end: "18.01.2024", status: "Ongoing" },
        { id: 10, location: "Hospital 10, address 10", start: "10.01.2024", end: "19.01.2024", status: "Archived" },
        { id: 11, location: "Hospital 11, address 11", start: "11.01.2024", end: "20.01.2024", status: "Ongoing" },
        { id: 12, location: "Hospital 12, address 12", start: "12.01.2024", end: "21.01.2024", status: "Archived" },
        { id: 13, location: "Hospital 13, address 13", start: "13.01.2024", end: "22.01.2024", status: "Ongoing" },
        { id: 14, location: "Hospital 14, address 14", start: "14.01.2024", end: "23.01.2024", status: "Archived" },
        { id: 15, location: "Hospital 15, address 15", start: "15.01.2024", end: "24.01.2024", status: "Ongoing" },
    ];

    let currentPage = 1;
    const itemsPerPage = 5;
    let sortCriteria = { key: 'location', order: 'asc' };

    function sortData(key) {
        let order = 'asc';
        if (sortCriteria.key === key && sortCriteria.order === 'asc') {
            order = 'desc';
        }
        const sortedData = [...data].sort((a, b) => {
            if (key === 'status') {
                return order === 'asc' ? a[key].localeCompare(b[key]) : b[key].localeCompare(a[key]);
            } else {
                return order === 'asc' ? a[key].localeCompare(b[key]) : b[key].localeCompare(a[key]);
            }
        });
        sortCriteria = { key, order };
        currentPage = 1;
        data = sortedData;
        renderTable();
        renderPagination();
        updateSortIcons(); // Обновление иконок сортировки после сортировки
    }

    function renderTable() {
        const tableBody = document.getElementById('hospitalization-table-body');
        tableBody.innerHTML = '';

        const indexOfLastItem = currentPage * itemsPerPage;
        const indexOfFirstItem = indexOfLastItem - itemsPerPage;
        const currentItems = data.slice(indexOfFirstItem, indexOfLastItem);

        currentItems.forEach(row => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${row.location}</td>
                <td>${row.start}</td>
                <td>${row.end}</td>
                <td>${row.status}</td>
            `;
            tableBody.appendChild(tr);
        });
    }

    function renderPagination() {
        const pagination = document.getElementById('pagination');
        pagination.innerHTML = '';

        const totalPages = Math.ceil(data.length / itemsPerPage);
        const pageNumbers = [];
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

        if (currentPage > 1) {
            const prevButton = document.createElement('button');
            prevButton.className = 'pageNumber';
            prevButton.textContent = 'Previous';
            prevButton.addEventListener('click', () => handlePageChange(currentPage - 1));
            pagination.appendChild(prevButton);
        }

        for (let i = startPage; i <= endPage; i++) {
            const pageButton = document.createElement('button');
            pageButton.className = `pageNumber ${currentPage === i ? 'active' : ''}`;
            pageButton.textContent = i;
            pageButton.addEventListener('click', () => handlePageChange(i));
            pagination.appendChild(pageButton);
        }

        if (currentPage < totalPages) {
            const nextButton = document.createElement('button');
            nextButton.className = 'pageNumber';
            nextButton.textContent = 'Next';
            nextButton.addEventListener('click', () => handlePageChange(currentPage + 1));
            pagination.appendChild(nextButton);
        }
    }

    function handlePageChange(pageNumber) {
        currentPage = pageNumber;
        renderTable();
        renderPagination();
    }

    function updateSortIcons() {
        // Remove all rotated classes from chevrons
        const chevrons = document.querySelectorAll('.fa-chevron-right');
        chevrons.forEach(chevron => chevron.classList.remove('rotated'));

        // Find and rotate the correct chevron
        const headers = document.querySelectorAll('.sortable');
        headers.forEach(header => {
            const chevron = header.querySelector('.fa-chevron-right');
            if (header.id === `${sortCriteria.key}-header`) {
                chevron.classList.add('rotated');
                if (sortCriteria.order === 'desc') {
                    chevron.classList.add('desc');
                } else {
                    chevron.classList.add('asc');
                }
            }
        });
    }

    document.getElementById('location-header').addEventListener('click', () => sortData('location'));
    document.getElementById('status-header').addEventListener('click', () => sortData('status'));

    renderTable();
    renderPagination();
});
