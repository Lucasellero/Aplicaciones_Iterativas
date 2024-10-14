export const validateUsername = (username) => {
    return username.trim() !== "";
  };

  export const validatePassword = (password) => {
    const hasUpperCase = /[A-Z]/.test(password);
    const hasNumber = /\d/.test(password);
    const isLongEnough = password.length > 8;

    return hasUpperCase && hasNumber && isLongEnough; 
  };

  export const validateConfirmPassword = (password, confirmPassword) => {
    return password === confirmPassword; 
  };

  export const validateEmail = (email) => {
    return email.includes("@"); 
  };

  export const validateRole = (role) => {
    return role === "ADMIN" || role === "USER"; 
  };

  export const validateName = (name) => {
    return /^[a-zA-Z]+$/.test(name); 
  };

  export const validateAddress = (address) => {
    return address.trim() !== ""; 
  };

  export const validatePhoneNumber = (phoneNumber) => {
    return /^\d+$/.test(phoneNumber);
  };