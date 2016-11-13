$(function() {
    $('.plainPassword').change(function() {
    	var plain = $( this ).val();
    	var encrypted = CryptoJS.AES.encrypt(plain, 'secret');
    	$('.encryptedPassword').val(encrypted);
    });
});