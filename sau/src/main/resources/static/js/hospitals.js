document.addEventListener("DOMContentLoaded", function () {
    let data = [
        { id: 1, hospital: "Hospital A", address: 'Address 1', laboratory: "Yes", wards: '10/20' },
        { id: 2, hospital: "Hospital B", address: 'Address 2', laboratory: "No", wards: '5/15' },
        { id: 3, hospital: "Hospital C", address: 'Address 3', laboratory: "Yes", wards: '8/25' },
        { id: 4, hospital: "Hospital D", address: 'Address 4', laboratory: "No", wards: '12/30' },
        { id: 5, hospital: "Hospital E", address: 'Address 5', laboratory: "Yes", wards: '15/18' },
        { id: 6, hospital: "Hospital F", address: 'Address 6', laboratory: "No", wards: '7/22' },
        { id: 7, hospital: "Hospital G", address: 'Address 7', laboratory: "Yes", wards: '9/24' },
        { id: 8, hospital: "Hospital H", address: 'Address 8', laboratory: "No", wards: '11/28' },
        { id: 9, hospital: "Hospital I", address: 'Address 9', laboratory: "Yes", wards: '14/21' },
        { id: 10, hospital: "Hospital J", address: 'Address 10', laboratory: "No", wards: '6/16' },
        { id: 11, hospital: "Hospital K", address: 'Address 11', laboratory: "Yes", wards: '13/26' },
        { id: 12, hospital: "Hospital L", address: 'Address 12', laboratory: "No", wards: '4/14' },
    ];

    let currentPage = 1;
    const itemsPerPage = 5;
    let sortCriteria = { key: 'laboratory', order: 'asc' };

    function sortData(key) {
        let order = 'asc';
        if (sortCriteria.key === key && sortCriteria.order === 'asc') {
            order = 'desc';
        }
        const sortedData = [...data].sort((a, b) => {
            if (key === 'laboratory') {
                return order === 'asc' ? (a[key] === 'Yes' ? -1 : 1) : (a[key] === 'Yes' ? 1 : -1);
            } else if (key === 'wards') {
                const aParts = a[key].split('/');
                const bParts = b[key].split('/');
                return order === 'asc' ? (parseInt(aParts[0], 10) - parseInt(bParts[0], 10)) : (parseInt(bParts[0], 10) - parseInt(aParts[0], 10));
            }
            return order === 'asc' ? a[key].localeCompare(b[key], undefined, { numeric: true, sensitivity: 'base' }) : b[key].localeCompare(a[key], undefined, { numeric: true, sensitivity: 'base' });
        });
        sortCriteria = { key, order };
        currentPage = 1;
        data = sortedData;
        renderTable();
        renderPagination();
        updateSortIcons();
    }

    function renderTable() {
        const tableBody = document.getElementById('hospital-table-body');
        tableBody.innerHTML = '';

        const indexOfLastItem = currentPage * itemsPerPage;
        const indexOfFirstItem = indexOfLastItem - itemsPerPage;
        const currentItems = data.slice(indexOfFirstItem, indexOfLastItem);

        currentItems.forEach(row => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${row.hospital}</td>
                <td>${row.address}</td>
                <td>${row.laboratory}</td>
                <td>${row.wards}</td>
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
        const headers = document.querySelectorAll('.sortable');
        headers.forEach(header => {
            const chevron = header.querySelector('.fa-chevron-right');
            chevron.classList.remove('rotated', 'asc', 'desc');
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

    document.getElementById('laboratory-header').addEventListener('click', () => sortData('laboratory'));
    document.getElementById('wards-header').addEventListener('click', () => sortData('wards'));

    renderTable();
    renderPagination();
});
