$(function() {
    $('.plainPassword').change(function() {
    	var plain = $( this ).val();
    	onPlainPasswordchange(plain);
    });
    
    $('.storePasswordButton').click(function() {
    	var master = $('.loginField').val();
    	sessionStorage.setItem('master', hash1(master));
    });
    
    $('.clearPasswordButton').click(function() {
    	sessionStorage.removeItem('master');
    });
    
    $('.generatePassword').click(function() {
    	var random = generateRandom();
    	$('.plainPassword').val(random);
    	onPlainPasswordchange(random);
    });
    
    $('.showPassword').click(function() {
    	var id = $(this).data('id');
    	var enc = $(this).siblings('.hiddenAccPassword').val();
    	var master = sessionStorage.getItem('master');
    	var decrypted = decryption(enc, master);
    	$(this).siblings('.outputPassword').val(decrypted);
    	$(this).css('visibility','hidden');
    });    
    
    var onPlainPasswordchange = function(plain) {
    	var masterPassword = sessionStorage.getItem('master');
    	var obj = encryption(plain, masterPassword);
    	$('.encryptedPassword').val(obj.enc);
    }
    
});

//*** Wrapper ***//

var encryption = function(message, key) {
	var salt = CryptoJS.lib.WordArray.random(512/8);
	var iv = CryptoJS.lib.WordArray.random(512/8);
	var key512Bits1000Iterations = CryptoJS.PBKDF2(key, salt, { keySize: 512/32, iterations: 1000 });
	var encrypted = CryptoJS.AES.encrypt('test',key512Bits1000Iterations , { iv: iv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 });
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
	return CryptoJS.SHA256("Message");
}
var hash2 = function(message) {
	return CryptoJS.SHA512("Message");
}
var validateHash = function(message, expected) {
	
}
var generateRandom = function() {
	return CryptoJS.lib.WordArray.random(128/8);
}
