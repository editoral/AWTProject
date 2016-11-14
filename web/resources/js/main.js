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
    
    var onPlainPasswordchange = function(plain) {
    	var masterPassword = sessionStorage.getItem('master');
    	var encrypted = encryption(plain, masterPassword);
    	$('.encryptedPassword').val(encrypted);
    }
    
    //*** Wrapper ***//
    
    var encryption = function(message, key) {
    	return CryptoJS.AES.encrypt(message, key);
    }
    var decryption = function(enc, key) {
    	
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
});