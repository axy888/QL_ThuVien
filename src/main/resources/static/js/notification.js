function handleNotification() {
    var message = "[[${message}]]".trim();
    var type = "[[${type}]]".trim();

    if (message) {
        showNotification(message, type);
    }
}

function showNotification(message,type) {
    var notification = $('#notification');
    if (type === 'error') {
        notification.css('background-color', 'red');}
    else
    {
        notification.css('background-color', '#4CAF50');
    }
    notification.text(message);
    notification.fadeIn();
    setTimeout(function() {
        notification.fadeOut();
    }, 2000);
}