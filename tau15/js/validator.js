// js module
exports.valid = function(form){
  form.className = "invalid";
  
  let formInput = null;
  for (let i = 0; i < form.children.length; i++) {
    if(form.children[i].tagName == "INPUT") formInput = form.children[i];
  }
  if(formInput) {
    const value = formInput.value;
    if(value.length < 3) return;
    if(!value.match("\d+$")) {
      return
    };
    form.className = "";
    return;
  }
};