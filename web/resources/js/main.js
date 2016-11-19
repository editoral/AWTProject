$(function() {
    $('.plainPassword').change(function() {
    	var plain = $( this ).val();
    	onPlainPasswordchange(plain);
    });
    
    $('.storePasswordButton').click(function() {
    	var master = $('.plainPasswordLogin').val();
    	sessionStorage.setItem('master', hash1(master));
    });
    
    $('.clearPasswordButton').click(function() {
    	sessionStorage.removeItem('master');
    });
    
    $('.saveButton').attr('disabled','disabled');
    $('.generatePassword').click(function() {
    	$('.saveButton').attr('disabled','disabled');
    	var random = generateRandom().toString();
    	$('.plainPassword').val(random);
    	onPlainPasswordchange(random);
    });
    
    $('.showPassword').click(function() {
    	var id = $(this).data('id');
    	var enc = $(this).siblings('.hiddenAccPassword').val();
    	var salt = $(this).siblings('.hiddenSalt').children().val();
    	var iv = $(this).siblings('.hiddenIV').children().val();
    	var master = sessionStorage.getItem('master');
    	var decrypted = decryption(enc, master, salt, iv);
    	$(this).siblings('.outputPassword').html(decrypted);
    	$(this).css('visibility','hidden');
    });    
    
    $('.readonly').children().attr('readonly','readonly');
    
    $('.plainPasswordLogin').change(function() {
    	var plain = $( this ).val();
    	$('.loginButton').attr('disabled','disabled');
    	$('.registerButton').attr('disabled','disabled');
    	$('.reEncrypt').attr('disabled','disabled');
    	$('.hiddenLoginHash').children().val(hash2(plain).toString());
    	$('.loginButton').removeAttr('disabled');
    	$('.registerButton').removeAttr('disabled');
    	$('.reEncrypt').removeAttr('disabled');
    });
    
    var onPlainPasswordchange = function(plain) {
    	$('.saveButton').attr('disabled','disabled');
    	var masterPassword = sessionStorage.getItem('master');
    	var obj = encryption(plain, masterPassword);
    	$('.encryptedPassword').val(obj.enc);
    	$('.hiddenSalt').children().val(obj.salt.toString());
    	$('.hiddenIV').children().val(obj.iv.toString());
    	$('.saveButton').removeAttr('disabled');
    }
    
    $('.changeButton').attr('disabled','disabled');
    $('.reEncrypt').click(function() {
    	$('.accPassword').each(function() {
        	var enc = $(this).children('.hiddenAccPassword').children().val();
        	var salt = $(this).children('.hiddenSalt').children().val();
        	var iv = $(this).children('.hiddenIV').children().val();
        	var master = sessionStorage.getItem('master');
        	var decrypted = decryption(enc, master, salt, iv);
        	master = hash1($('.plainPasswordLogin').val());
        	var obj = encryption(decrypted, master.toString());
        	$(this).children('.hiddenAccPassword').children().val(obj.enc.toString());
        	$(this).children('.hiddenSalt').children().val(obj.salt.toString());
        	$(this).children('.hiddenIV').children().val(obj.iv.toString());
    	});
    	$('.changeButton').removeAttr('disabled');
    });
});

//*** Wrapper ***//

var encryption = function(message, key) {
	var salt = CryptoJS.lib.WordArray.random(512/8);
	var iv = CryptoJS.lib.WordArray.random(512/8);
	var key512Bits1000Iterations = CryptoJS.PBKDF2(key, salt, { keySize: 512/32, iterations: 1000 });
	var encrypted = CryptoJS.AES.encrypt(message,key512Bits1000Iterations , { iv: iv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 });
	var decrypted = CryptoJS.AES.decrypt(encrypted,key512Bits1000Iterations , { iv: iv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 });
	var resultString = decrypted.toString(CryptoJS.enc.Utf8);
	var result = {
		salt: salt,
		iv: iv,
		enc: encrypted
	}
	return result;
}
var decryption = function(enc, key, salt, iv) {
    var salt = CryptoJS.enc.Hex.parse(salt);
    var iv = CryptoJS.enc.Hex.parse(iv);
    var key512Bits1000Iterations = CryptoJS.PBKDF2(key, salt, { keySize: 512/32, iterations: 1000 });
    var decrypted = CryptoJS.AES.decrypt(enc,key512Bits1000Iterations , { iv: iv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 });
	return decrypted.toString(CryptoJS.enc.Utf8);
}
var hash1 = function(message) {
	return CryptoJS.SHA256(message);
}
var hash2 = function(message) {
	return CryptoJS.SHA512(message);
}
var validateHash = function(message, expected) {
	
}
var generateRandom = function() {
	return CryptoJS.lib.WordArray.random(128/8);
}
