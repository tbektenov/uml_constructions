document.addEventListener("DOMContentLoaded", function () {
    const data = [
        { id: 1, doctor: "Specialization 1", location: 'Hospital 1, address 1', date: "01.01.2024", time: '09:00' },
        { id: 2, doctor: "Specialization 2", location: 'Hospital 2, address 2', date: "02.01.2024", time: '10:00' },
        { id: 3, doctor: "Specialization 3", location: 'Hospital 3, address 3', date: "03.01.2024", time: '11:00' },
        { id: 4, doctor: "Specialization 4", location: 'Hospital 4, address 4', date: "04.01.2024", time: '12:00' },
        { id: 5, doctor: "Specialization 5", location: 'Hospital 5, address 5', date: "05.01.2024", time: '13:00' },
        { id: 6, doctor: "Specialization 6", location: 'Hospital 6, address 6', date: "06.01.2024", time: '14:00' },
        { id: 7, doctor: "Specialization 7", location: 'Hospital 7, address 7', date: "07.01.2024", time: '15:00' },
        { id: 8, doctor: "Specialization 8", location: 'Hospital 8, address 8', date: "08.01.2024", time: '16:00' },
        { id: 9, doctor: "Specialization 9", location: 'Hospital 9, address 9', date: "09.01.2024", time: '17:00' },
        { id: 10, doctor: "Specialization 10", location: 'Hospital 10, address 10', date: "10.01.2024", time: '18:00' },
        { id: 11, doctor: "Specialization 11", location: 'Hospital 11, address 11', date: "11.01.2024", time: '19:00' },
        { id: 12, doctor: "Specialization 12", location: 'Hospital 12, address 12', date: "12.01.2024", time: '20:00' },
        { id: 13, doctor: "Specialization 13", location: 'Hospital 13, address 13', date: "13.01.2024", time: '21:00' },
    ];

    let currentPage = 1;
    const itemsPerPage = 5;

    function renderTable() {
        const tableBody = document.getElementById('appointment-table-body');
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
            }
            else if (currentPage + Math.floor(maxPagesToShow / 2) >= totalPages) {
                startPage = totalPages - maxPagesToShow + 1;
            }
        }

        // Render 'Previous' button
        if (currentPage > 1) {
            const prevButton = document.createElement('button');
            prevButton.className = 'pageNumber';
            prevButton.textContent = 'Previous';
            prevButton.addEventListener('click', () => handlePageChange(currentPage - 1));
            pagination.appendChild(prevButton);
        }

        // Render page numbers within the determined range
        for (let i = startPage; i <= endPage; i++) {
            const pageNumberButton = document.createElement('button');
            pageNumberButton.className = `pageNumber ${currentPage === i ? 'active' : ''}`;
            pageNumberButton.textContent = i;
            pageNumberButton.addEventListener('click', () => handlePageChange(i));
            pagination.appendChild(pageNumberButton);
        }

        // Render 'Next' button
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

    renderTable();
    renderPagination();
});
