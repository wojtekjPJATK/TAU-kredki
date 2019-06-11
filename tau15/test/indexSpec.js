const { valid } = require("../js/validator.js"); // load module

describe("cenzor", function () {
    
    beforeEach(function () {
        let elem = document.createElement('div');
        elem.id = "form";
        elem.innerHTML = `
        <div class="label">Color</div>
        <input id="colorInput" type="text" name="colorInput">
        `;
        document.body.appendChild(elem);
        document.addEventListener('keydown', function(e){
            let input = document.getElementById('colorInput');
            input.value += "d";            
            valid(elem);
         });
    });

    afterEach(function () {
        $('#form').remove();
    });

    it("valid input should not have invalid class", function () {
        let elem = document.getElementById('form');
        let input = document.getElementById('colorInput');
        input.value = "Red"
        valid(elem);
        expect(elem.className).not.toEqual("invalid");
    });

    it("input with number should have invalid class", function () {
        let elem = document.getElementById('form');
        let input = document.getElementById('colorInput');
        input.value = "123"
        valid(elem);
        expect(elem.className).toEqual("invalid");
    });

    it("empty input should have invalid class", function () {
        let elem = document.getElementById('form');
        let input = document.getElementById('colorInput');
        input.value = ""
        valid(elem);
        expect(elem.className).toEqual("invalid");
    });

    it("input shorter than 3 letter should have invalid class", function () {
        let elem = document.getElementById('form');
        let input = document.getElementById('colorInput');
        input.value = "Re"
        valid(elem);
        expect(elem.className).toEqual("invalid");
        var event = document.createEvent('Event');
        event.key = 67;
        event.initEvent('keydown');
        document.dispatchEvent(event);
        expect(elem.className).not.toEqual("invalid");        
    });

    it("test after keydown event", function () {
        let elem = document.getElementById('form');
        let input = document.getElementById('colorInput');
        input.value = "Re"
        valid(elem);
        expect(elem.className).toEqual("invalid");
        var event = document.createEvent('Event');
        event.key = 67;
        event.initEvent('keydown');
        document.dispatchEvent(event);
        expect(elem.className).not.toEqual("invalid");        
    });


});

