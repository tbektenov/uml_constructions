document.addEventListener('DOMContentLoaded', function () {
    let data = [
        { id: 1, doctor: "Specialization 1", location: 'Hospital 1, address 1', date: "01.01.2024", time: '09:00', status: 'Upcoming' },
        { id: 2, doctor: "Specialization 2", location: 'Hospital 2, address 2', date: "02.01.2024", time: '10:00', status: 'Upcoming' },
        { id: 3, doctor: "Specialization 3", location: 'Hospital 3, address 3', date: "03.01.2024", time: '11:00', status: 'Upcoming' },
        { id: 4, doctor: "Specialization 4", location: 'Hospital 4, address 4', date: "04.01.2024", time: '12:00', status: 'Upcoming' },
        { id: 5, doctor: "Specialization 5", location: 'Hospital 5, address 5', date: "05.01.2024", time: '13:00', status: 'Upcoming' },
        { id: 6, doctor: "Specialization 6", location: 'Hospital 6, address 6', date: "06.01.2024", time: '14:00', status: 'Archived' },
        { id: 7, doctor: "Specialization 7", location: 'Hospital 7, address 7', date: "07.01.2024", time: '15:00', status: 'Archived' },
        { id: 8, doctor: "Specialization 8", location: 'Hospital 8, address 8', date: "08.01.2024", time: '16:00', status: 'Archived' },
        { id: 9, doctor: "Specialization 9", location: 'Hospital 9, address 9', date: "09.01.2024", time: '17:00', status: 'Archived' },
        { id: 10, doctor: "Specialization 10", location: 'Hospital 10, address 10', date: "10.01.2024", time: '18:00', status: 'Archived' },
        { id: 11, doctor: "Specialization 11", location: 'Hospital 11, address 11', date: "11.01.2024", time: '19:00', status: 'Archived' },
        { id: 12, doctor: "Specialization 12", location: 'Hospital 12, address 12', date: "12.01.2024", time: '20:00', status: 'Archived' },
        { id: 13, doctor: "Specialization 13", location: 'Hospital 13, address 13', date: "13.01.2024", time: '21:00', status: 'Archived' },
    ];

    let currentPage = 1;
    const itemsPerPage = 5;
    let sortCriteria = { key: 'date', order: 'asc' };

    function renderTable() {
        const tableBody = document.getElementById('appointmentsTableBody');
        tableBody.innerHTML = '';

        const indexOfLastItem = currentPage * itemsPerPage;
        const indexOfFirstItem = indexOfLastItem - itemsPerPage;
        const currentItems = data.slice(indexOfFirstItem, indexOfLastItem);

        currentItems.forEach(row => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${row.doctor}</td>
                <td>${row.location}</td>
                <td>${row.date}</td>
                <td>${row.status}</td>
            `;
            tableBody.appendChild(tr);
        });
    }

    function renderPagination() {
        const pagination = document.getElementById('pagination');
        pagination.innerHTML = '';

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

        if (currentPage > 1) {
            const prevButton = createPageButton('Previous', currentPage - 1);
            pagination.appendChild(prevButton);
        }

        for (let i = startPage; i <= endPage; i++) {
            const pageButton = createPageButton(i, i);
            pagination.appendChild(pageButton);
        }

        if (currentPage < totalPages) {
            const nextButton = createPageButton('Next', currentPage + 1);
            pagination.appendChild(nextButton);
        }
    }

    function createPageButton(text, pageNumber) {
        const button = document.createElement('button');
        button.textContent = text;
        button.className = 'pageNumber';
        if (pageNumber === currentPage) {
            button.classList.add('active');
        }
        button.addEventListener('click', () => {
            handlePageChange(pageNumber);
        });
        return button;
    }

    function handlePageChange(pageNumber) {
        currentPage = pageNumber;
        renderTable();
        renderPagination();
    }

    function sortData(key) {
        let order = 'asc';
        if (sortCriteria.key === key && sortCriteria.order === 'asc') {
            order = 'desc';
        }
        const sortedData = [...data].sort((a, b) => {
            if (key === 'date') {
                const dateA = new Date(a.date);
                const dateB = new Date(b.date);
                return order === 'asc' ? dateA - dateB : dateB - dateA;
            } else if (key === 'status') {
                return order === 'asc' ? a[key].localeCompare(b[key]) : b[key].localeCompare(a[key]);
            }
        });
        sortCriteria = { key, order };
        currentPage = 1;
        data = sortedData;
        renderTable();
        renderPagination();
    }

    // Initial render
    renderTable();
    renderPagination();

    // Attach click event listeners to sort headers
    document.getElementById('dateHeader').addEventListener('click', () => sortData('date'));
    document.getElementById('statusHeader').addEventListener('click', () => sortData('status'));
});
