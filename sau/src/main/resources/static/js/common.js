document.addEventListener('DOMContentLoaded', () => {
    const viewClassesButton = document.getElementById('view-classes-button');
    const viewAllMembersButton = document.getElementById('view-all-members-button');

    if (viewClassesButton) {
        // Add event listener to view classes button
        viewClassesButton.addEventListener('click', () => {
            window.location.href = 'classes.html';
        });
    }

    if (viewAllMembersButton) {
        // Add event listener to view all members button
        viewAllMembersButton.addEventListener('click', () => {
            window.location.href = 'allMembers.html';
        });
    }
});
