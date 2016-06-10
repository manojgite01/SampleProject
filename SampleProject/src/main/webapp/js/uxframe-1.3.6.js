/*!  * Copyright (c) 2016 Deere & Company. All Rights Reserved */

/*
* A set of helper functions used throughout the UXFrame distribution.
*/


/* IE8 Compatibility Functions */
if(!document.querySelectorAll)
(function(d,s){d=document,s=d.createStyleSheet();d.querySelectorAll=function(r,c,i,j,a){a=d.all,c=[],r=r.replace(/\[for\b/gi,'[htmlFor').split(',');for(i=r.length;i--;){s.addRule(r[i],'k:v');for(j=a.length;j--;)a[j].currentStyle.k&&c.push(a[j]);s.removeRule(0)}return c}})();
if ( typeof Object.getPrototypeOf !== "function" ) {
  if ( typeof "test".__proto__ === "object" ) {
    Object.getPrototypeOf = function(object){
      return object.__proto__;
    };
  } else {
    Object.getPrototypeOf = function(object){
      // May break if the constructor has been tampered with
      return object.constructor.prototype;
    };
  }
}
Function.prototype.construct = function(aArgs) {
    // console.log(aArgs);
    var fConstructor = this, fNewConstr = function() { 
        fConstructor.apply(this, aArgs); 
    };
    fNewConstr.prototype = fConstructor.prototype;
    return new fNewConstr();
};
// if(!Object.getPrototypeOf) {
//   console.log('c');
//   if(({}).__proto__===Object.prototype&&([]).__proto__===Array.prototype) {
//     Object.getPrototypeOf=function getPrototypeOf(object) {
//       console.log('a');
//       return object.__proto__;
//     };
//   } else {
//     Object.getPrototypeOf=function getPrototypeOf(object) {
//       // May break if the constructor has been changed or removed
//       console.log('b');
//       return object.constructor?object.constructor.prototype:void 0;
//     };
//   }
// }

/* End IE8 Compatibility Functions */


/*
 * All exported functions are accessible through UXFrameHelper/$UXF.
 * This object by default contains a set of helper functions to abstract
 * away from strange browser behavior. Look at each function for more
 * detailed documentation.
 */
var UXFrameHelper = $UXF = (function() {

    var Help = function(element) {
        this.Register = {};
        this.componentId = 0;
    };


    Help.prototype = {

        /*
         * This is the function that all components initiall run their event
         * registration through. Right now, it just handles default registration
         * of events, but this could be expanded to handle other initial settings.
         */
        defaultRegister: function(func) {
            var self = this;
            self.addLoadEvent(function() {
                if (self.defaultBinds) func()
            });
        },

        /*
         * Simply a helper function force registration of all components.
         */
        registerAll: function() {
            for (var i in this.Register) {
                this.Register[i]();
            }
        },
        /*
         * Adds an event on window.load, or runs if it is already loaded
         */
        addLoadEvent: function (func) {
            if (document.readyState === "complete") {
                // console.log('ran');
                func();
            } else {
                var oldonload = window.onload;
                if (typeof window.onload != 'function') {
                    window.onload = func;
                } else {
                    window.onload = function() {
                        if (oldonload) {
                            oldonload();
                        }
                        func();
                    }
                }
            }
        },

        /*
         * Helper function for adding an event to a given element.
         * In addition to standard events, the string "immediate"
         * is also valid, which will cause the event to be immediately
         * executed.
         *
         * Fourth argument allows overriding of the bound object for the event
         * this is helpful when we directly bind the object to a DOM element.
         */
        addEvent: function(element, evnt, funct, bind) {
            var thisCont = (bind === undefined) ? element : bind;
            if (evnt == "immediate") {
                funct.call(thisCont, evnt);
            } else {
                if (element.attachEvent)
                    return element.attachEvent('on'+evnt, function() {
                        // We use .call() here so that the $this context is set correctly.
                        funct.call(thisCont, evnt);
                    });
                else
                    return element.addEventListener(evnt, funct.bind(thisCont), false);
            }
        },

        triggerEvent: function(evntType, elmnt, eventType) {
            if (eventType == undefined) eventType = 'HTMLEvents';
            var evnt;
            if (document.createEvent) {
                evnt = document.createEvent(eventType);
                evnt.initEvent(evntType, true, true);
            } else {
                evnt = document.createEventObject();
                evnt.eventType = evntType;
            }

            evnt.eventName = eventType;

            if (document.createEvent) {
                elmnt.dispatchEvent(evnt);
            } else {
                elmnt.fireEvent('on'+evnt.eventType, evnt);
            }
        },

        stopProp: function(event) {
            if (event.stopPropagation){
                event.stopPropagation();
            }
            else if(window.event){
                window.event.cancelBubble=true;
            }
        },

        /*
         * Takes an element and a class string and returns a boolean 
         * if the element has the class
         */
        hasClass: function(element, cls) {
            return element.className.match(new RegExp('(\\s|^)'+cls+'(\\s|$)'));
        },

        /*
         * Removes class string from a given element.
         */
        removeClass: function(ele, cls){
            var reg = new RegExp('(\\s|^)'+cls+'(\\s|$)');
            ele.className = ele.className.replace(reg,' ');
        },

        /*
         * Adds class string from a given element.
         */
        addClass: function(ele, cls) {
            ele.className += " "+cls;
        },

        /*
         * Toggles class string from a given element.
         */
        toggleClass: function(element, clss) {
            var self = this;
            if (self.hasClass(element, clss)) {
                self.removeClass(element, clss);
            } else {
                self.addClass(element, clss);
            }

        },

        createDiv: function(cls, content) {
            var d = document.createElement('div');
            if (cls !== undefined) { d.className += (" " + cls); }
            if (content !== undefined) { d.innerHTML = content; }
            return d;
        },

        /*
         * Returns the previous sibiling. This can be helpful
         * because ie8 has some werid behaviour by default.
         */
        previousElementSibling: function(el) {
            if( el.previousElementSibling ) {
                return el.previousElementSibling;
            } else {
                while( el = el.previousSibling ) {
                    if( el.nodeType === 1 ) return el;
                }
            }
        },

        /*
         * Returns the next sibiling. This can be helpful
         * because ie8 has some werid behaviour by default.
         */
        nextElementSibling: function(el) {
            if( el.nextElementSibling ) {
                return el.nextElementSibling;
            } else {
                while( el = el.nextSibling ) {
                    if( el.nodeType === 1 ) return el;
                }
            }
        },

        /*
         * Returns either the element passed in, or if the argument
         * is a string, find the element with that Id.
         * This is used because we want people to be able to
         * select elements both with a tool like JQuery and by id.
         */
        elementOrId: function(a) {
            if (typeof a === "string" || a instanceof String) {
                return document.getElementById(a);
            } else {
                return a;
            }
        },

        registerObject: function(id, obj) {
            // console.log(arguments);
            var self = this;
            if (obj.uxfid === undefined) obj.uxfid = self.componentId++;
            var ele = document.getElementById(id);
            if (ele['uxfo'] === undefined) ele['uxfo'] = {};
            // ele['uxfo'][obj.uxfid] = new obj(ele);
            //  var args = Array.prototype.slice.call(arguments, 1);
            var args = Array.prototype.slice.call(arguments,2);
            args.unshift(ele);
            ele['uxfo'][obj.uxfid] = obj.construct(args);
            // console.log('logging from ro:', ele, ele.DP);

        },


        /*
         * This function is pretty much the crux of the helper functions.
         * This function takes care of adding a function to certain selected element
         * on a given event.
         * Passed in are:
         *      elementSelector: a string for querySelectorAll.
         *      evnt: a JS event.
         *      obj: the JS object that can be constructed using elements from the selector.
         *      func: a string which is the name of a function in obj.
         *      masterElement: if this is passed through, only querySelect internal
         *          to this element.
         * Returns:
         *      list of elements which had an event bound to them.
         */
        registerEvents: function(elementSelector, evnt, obj, func, masterElement) {
            var self = this;
            // var boundList = [];
            // In order to correctly not rebind a function, we assign a uxfid
            // to each obj type.
            if (obj.uxfid === undefined) obj.uxfid = self.componentId++;
            // We need to create an array of elements we're going to apply
            // this event to.
            var elements;
            // If the user passes one of these three objects directly, we don't
            // need to do any more work.
            if (!(elementSelector === document || elementSelector === window || elementSelector == document.documentElement)){
                // If the user doesn't provide an element, we query the whole document.
                var selectr = masterElement ? masterElement : document;
                // We're always selecting elements inside the .jd class.
                elementSelector = ".jd " + elementSelector;
                elements = selectr.querySelectorAll(elementSelector);
                // If we don't return any elements, it may be that the provided 
                // masterElement is what we're looking for qSA doesn't check
                // selectr, so we manually check for a match.
                elements = (elements.length == 0 && masterElement && masterElement.matches(elementSelector)) ? [masterElement] : elements;
            } else {
                // If it's document or something like that, we just put it in 
                // an array so the loop works.
                elements = [elementSelector];
            }
            for (var i = 0; i < elements.length; i++) {
                // We need to construct an element to check it's shouldBind value.
                var o = new obj(elements[i]);
                // We're storing data about DOM elements in e.uxfd.
                var uxfd = 'uxfd';
                // We also need a unique identifier for each possible componenet and event.
                // This assumes that the same component will not bind two of the 
                // same event to the same selector. 
                var uniq = obj.uxfid + '!' + elementSelector + '!' + evnt;

                // We need to ensure we don't double bind an event.
                if (!elements[i][uxfd]) elements[i][uxfd] = {};
                if (!elements[i][uxfd][uniq] && (o['shouldBind'] === undefined || o['shouldBind']())) {
                    self.addEvent(elements[i], evnt, function(e) {
                        // We construct a new object. I don't think we can
                        // use 'o' from above because of scope.
                        var x = new obj(this);
                        // call the function provided, passing in the event as
                        // an argument.
                        x[func](e);
                    });
                    // boundList.push(elements[i]);
                }
                // Once a component/selector/event has bound, we don't want
                // to bind it again. If it is vitally important, this value
                // can be set to false manually.
                elements[i][uxfd][uniq] = true;
            }
            // return boundList;
        }


    };

    var H = new Help();
    H.defaultBinds = true;

    /* 
     * By default all of the content of the page is hidden. Once it's loaded
     * we want the page to show.
     * We do this to avoid a flash of unstyled content.
     */
    H.addLoadEvent(function() { H.addClass(document.documentElement,'jd-loaded')});

    return H
}());


!function(H) {

    var Accordion = function(element) {
        this.$e = element;
        this.$pn = element.parentElement;
        this.$body = H.nextElementSibling(this.$e);
    }
    // Accordion.timeinstance = Date.now();

    Accordion.prototype = {
        toggle: function() {
            H.toggleClass(this.$pn, 'closed');
        }
    };

    H.Accordion = function(a) {
        return new Accordion(H.elementOrId(a));
    }

    H.Register.Accordion = function(e) {
        H.registerEvents('.accordion .title', 'click', Accordion, 'toggle', e);
    }

    H.defaultRegister(H.Register.Accordion);

}(UXFrameHelper);

!function(H) {

    var UserInputLabel = function(element) {
        this.$e = element;
        this.$tb = H.previousElementSibling(element);
    }

    UserInputLabel.selector = ".input-box .input-label";

    UserInputLabel.prototype = {

        setTextBoxFocus: function() {
            this.$tb.focus();
        }
    }

    ///

    var UserInputBox = function(element) {
        this.$tb = element;
    }

    UserInputBox.selector = ".input-box input";
    UserInputBox.activeLabel = "show-label";
    // UserInputBox.inlineSubmit = ".input-box .submit";

    UserInputBox.prototype = {

        setInitial: function() {
            var sib = H.nextElementSibling(this.$tb);
            if (sib && H.hasClass(sib, 'input-label') ) {
                H.addClass(this.$tb, UserInputBox.activeLabel);
            }
            // When the user goes back or forwards to a page they've already
            // filled in, it will get filled in automatically, we want to 
            // check this off the bat.
            this.checkContent();
        },

        checkContent: function() {
            var sib = H.nextElementSibling(this.$tb);
            if (sib && H.hasClass(sib, 'input-label') ) {

                if (this.$tb.value == "" && !H.hasClass(this.$tb, UserInputBox.activeLabel)) {
                    H.addClass(this.$tb, UserInputBox.activeLabel);
                    // Why IE8? This forces a repaint. I have no words.
                    H.addClass(this.$tb.parentElement,'z');
                    H.removeClass(this.$tb.parentElement,'z');
                }
                else if (this.$tb.value != "" && H.hasClass(this.$tb, UserInputBox.activeLabel)) {
                    H.removeClass(this.$tb, UserInputBox.activeLabel);
                    H.addClass(this.$tb.parentElement,'z');
                    H.removeClass(this.$tb.parentElement,'z');
                }
            }
        }
    }

    // H.registerEvents(UserInputLabel.selector, 'click', UserInputLabel, 'setTextBoxFocus');
    // H.registerEvents(UserInputBox.selector, 'immediate', UserInputBox, 'setInitial');
    // H.registerEvents(UserInputBox.selector, 'keyup', UserInputBox, 'checkContent');
    // H.registerEvents(UserInputBox.selector, 'input', UserInputBox, 'checkContent');

    // We use this method to re-register on a specific element

    H.Register.UserInputBox = function(e) {
        H.registerEvents(UserInputLabel.selector, 'click', UserInputLabel, 'setTextBoxFocus', e);
        H.registerEvents(UserInputBox.selector, 'immediate', UserInputBox, 'setInitial', e);
        H.registerEvents(UserInputBox.selector, 'keyup', UserInputBox, 'checkContent', e);
        H.registerEvents(UserInputBox.selector, 'input', UserInputBox, 'checkContent', e);
    }

    H.defaultRegister(H.Register.UserInputBox);

}(UXFrameHelper);

!function(H) {
    var ToolTip = function(element) {
        this.$e = element;
    }

    ToolTip.selector = '.tooltip';
    ToolTip.show = '.show';

    ToolTip.prototype = {
        toggle: function() {
            if (this.$e) H.toggleClass(this.$e, ToolTip.show);
        },
        show: function() {
            if (this.$e && !H.hasClass(this.$e, 'show')) {
                H.addClass(this.$e, 'show');
            }
        },
        hide: function() {
            if (this.$e && H.hasClass(this.$e, 'show')) {
                H.removeClass(this.$e, 'show');
            }
        },
        setText: function(text) {
            if (this.$e) this.$e.innerHTML = text;
        }
    }

    H.ToolTip = function(a) {
        return new ToolTip(H.elementOrId(a));
    }

}(UXFrameHelper);

/*
 * DatePick is the first object that utilizes the registerObject function.
 *
 * Note to future UXFrame dev: This is particularly messy, there is absolutely a
 * better way to manage internal state and rendering.
 */
!function(H) {
    var DatePick = function(element, initialDropDownDate, startDate, endDate) {
        this.$inputElement = element;
        if (this.$inputElement.parentElement) this.$inputBox = element.parentElement;

        this.$calendar = this.setInitialCalendar();

        // The possible range of dates to select. Anything outside is gray'd
        // and unselectable
        this.$dateRange = [startDate, endDate];

        // The date whose month is being shown.
        this.$displayDate = (initialDropDownDate) ? initialDropDownDate : new Date();

        // The currently selected date.
        this.$selectedDate;

        this.draw(false);
        H.addEvent(this.$inputElement, 'keydown', this.textBoxKeyDown, this);
        H.addEvent(this.$inputElement, 'input', this.textBoxInput, this);
        // H.addEvent(this.$calendar, 'keydown', this.calendarKeyDown, this);
        // H.addEvent(this.$inputElement, 'focusout', this.textBoxFocusOut, this);
        H.addEvent(this.$inputElement, 'click', this.textBoxClicked, this);
        H.addEvent(document, 'click', this.closeCalendar, this);
    }
    DatePick.months = new Array("Jan", "Feb", "Mar",
                                "Apr", "May", "Jun",
                                "Jul", "Aug", "Sep",
                                "Oct", "Nov", "Dec" );
    DatePick.weekDays = new Array("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa");

    DatePick.sameDate = function(d1,d2) {
        return d1 && d2 && (d1.getDate() === d2.getDate() && d1.getMonth() === d2.getMonth() && d1.getFullYear() === d2.getFullYear()); 
    };
    DatePick.inRange = function(v1, m , v2) {
        var inr = true;
        if ( v1 === undefined ) {
            if ( v2 === undefined ) return true;
            return m <= v2;
            // else return m <= v2;
        } else if ( v2 === undefined ){
            return v1 <= m;
        }
        return (v1 <= m) && (m <= v2);
    }
    DatePick.stdFormat = function(d) {
        return d.getDate() + '-' + DatePick.months[d.getMonth()] + '-' + d.getFullYear();

    }

    DatePick.prototype = {
        draw: function(setFocus) {
            while (this.$calendar.firstChild) {
                this.$calendar.removeChild(this.$calendar.firstChild);
            }
            var month = H.createDiv('month');
            // console.log(this.$displayDate);
            month.appendChild(this.generateTitle());
            month.appendChild(this.generateWeeks());

            this.$calendar.appendChild(month);
            if (setFocus) this.$inputElement.focus();
            // if (!document.activeElement == this.$inputElement) this.$inputElement.focus();
        },
        generateTitle: function() {
            var title = H.createDiv('title');
            var name = H.createDiv('name', DatePick.months[this.$displayDate.getMonth()] + ' ' + this.$displayDate.getFullYear());
            var cl = H.createDiv('control left', ' ');
            var cr = H.createDiv('control right', ' ');
            H.addEvent(cl, 'click', this.changeDisplayMonth(-1), this);
            H.addEvent(cr, 'click', this.changeDisplayMonth(1), this);
            title.appendChild(cl);
            title.appendChild(name);
            title.appendChild(cr);
            return title;

        },
        generateWeeks: function() {
            var dateCycle = new Date(this.$displayDate.getFullYear(), this.$displayDate.getMonth());
            m = H.createDiv('week-container');
            var wktitle = H.createDiv('week-title');
            for (var i = 0; i < DatePick.weekDays.length; i++) {
                wktitle.appendChild(H.createDiv('lbl', DatePick.weekDays[i]));
            }
            m.appendChild(wktitle);
            var wk = H.createDiv('week');
            for (var i = 0; i< dateCycle.getDay(); i++) {
                wk.appendChild(H.createDiv('day no', ' '));
            }
            while ( dateCycle.getMonth() === this.$displayDate.getMonth() ) {
                var day = H.createDiv('day', dateCycle.getDate())
                day.d = dateCycle.getDate();
                day.m = dateCycle.getMonth();
                day.y = dateCycle.getFullYear();
                if (DatePick.sameDate(dateCycle, this.$selectedDate)) H.addClass(day, 'selected');
                if (DatePick.sameDate(dateCycle, new Date())) H.addClass(day, 'today');
                // if ((this.$dateRange[0] && dateCycle >= this.$dateRange[0] )
                //     || (this.$dateRange[1] && dateCycle <= this.$dateRange[1])) {
                if (DatePick.inRange(this.$dateRange[0], dateCycle, this.$dateRange[1])){
                    H.addEvent(day, 'click', this.setSelectedDate(true, true, day.y, day.m, day.d), this);
                    // var setDay = this.setSelectedDate(true, day.y, day.m, day.d)
                    // H.addEvent(day, 'click', function(e) {
                    //     setDay.call(this, e);
                    //     this.$inputElement.focus();
                    // }, this);
                } else {
                    H.addClass(day,'inactive');
                }
                wk.appendChild(day);
                if (dateCycle.getDay() === 6){
                    m.appendChild(wk);
                    wk = H.createDiv('week');
                } 
                dateCycle.setDate(dateCycle.getDate() + 1);
            }
            m.appendChild(wk);
            return m;
        },
        setSelectedDate: function(setInput, setFocus, y, m, d) {
            // console.log('generatedfunc', y, m, d);
            return function(e) {
                // console.log('setting dis', y);
                if ( y === undefined ) {
                    this.$selectedDate = undefined;
                    var inV = '';
                } else {
                    this.$selectedDate = (m === undefined && d === undefined) ? new Date(y) : new Date(y,m,d);
                    var inV = DatePick.stdFormat(this.$selectedDate);
                }
                if (setInput) this.$inputElement.value = inV;
                // this.$inputElement.value = setInput ? DatePick.stdFormat(this.$selectedDate) : '';
                // this.$inputElement.value = this.$selectedDate.getDate() + '-' + DatePick.months[this.$selectedDate.getMonth()] + '-' + this.$selectedDate.getFullYear();
                this.draw(setFocus);
                // this.$inputElement.focus();
            }
        },
        changeDisplayMonth: function(v){
            return function(e) {
                this.$displayDate.setMonth(this.$displayDate.getMonth() + v);
                this.draw(true);
            }
        },
        setInitialCalendar: function(){
            var cal = H.createDiv('calendar');
            H.addEvent(cal,'click', function(e) { H.stopProp(e);}, this);
            this.$inputBox.appendChild(cal);
            return cal;
        },
        closeCalendar: function(e) {
            this.parseInputContent(true, true);
            if (H.hasClass(this.$inputElement, 'active')) {
                H.removeClass(this.$inputElement, 'active');
            }
            if (H.hasClass(this.$inputElement, 'focus')) {
                H.removeClass(this.$inputElement, 'focus');
            }
        },
        textBoxClicked: function(e){
            H.stopProp(e);
            if (!H.hasClass(this.$inputElement, 'focus')) {
                H.addClass(this.$inputElement, 'focus');
            }
        },
        parseInputContent: function(reset, setBox){
            // console.log(e.KEYPRESS);
            // Pretty basic regexes, there could still be invalid dates, we always need to check that it's a valid date
            // For example, these match 37-Jan-0001
            if (this.$inputElement.value === '') return;
            var parsed, d, m, y;
            var stdDate = new RegExp("(\\s|^)([0-9]{1,2})-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-([0-9]{4})(\\s|$)");
            var usDate = new RegExp("(\s|^)([0-9]{1,2})\/([0-9]{1,2})\/([0-9]{2}|[0-9]{4})(\s|$)");
            // var usDate4 = new RegExp("(\s|^)([0-9]{1,2})\/([0-9]{1,2})\/([0-9]{2,4})(\s|$)");
            if (parsed = stdDate.exec(this.$inputElement.value)) {
                // console.log('matched - stdDate')
                d = parseInt(parsed[02]);
                m = DatePick.months.indexOf(parsed[03]);
                y = parseInt(parsed[04]);
            }
            // In either of the slash formats, we're going to assume US order, unless there's evidence to the contrary
            else if (parsed = usDate.exec(this.$inputElement.value)){
                var thisYear = new Date().getFullYear();
                var m = parseInt(parsed[02]);
                var d = parseInt(parsed[03]);
                var y = parseInt(parsed[04]);
                if (m > 12) {
                    var temp = m; m = d; d = temp;
                }
                m--;
                if (y<100) {
                    var y19 = y+1900, y20 = y+2000;
                    y = (Math.abs(y19-thisYear) < Math.abs(y20-thisYear)) ? y19 : y20;
                }
            }
            candiDate = new Date(y,m,d);
            // If something like 53 was passed in as date, this should fail.
            // 
            // NOTE: This whole part is messy. This and the setSelectedDate function
            // need to be refactored in the near future.
            // 
            if (!(candiDate.getDate() === d && candiDate.getMonth() === m && candiDate.getFullYear() === y) ||
               !(DatePick.inRange(this.$dateRange[0], candiDate, this.$dateRange[1]))){
               reset = false;
               candiDate = this.$selectedDate ? this.$selectedDate : undefined;
           } else {
               reset = true;
           }
            // if (this.$dateRange[0] && this.$dateRange[1] && 
            //     candiDate >= this.$dateRange[0] && candiDate <= this.$dateRange[1]) {
            //         candiDate = this.$selectedDate;
            // }
            if (reset) {
                this.$displayDate = candiDate ? candiDate : this.$displayDate;
                this.setSelectedDate(setBox, false, candiDate).call(this);
            }
            // console.log(reset, candiDate);
        },
        textBoxInput: function(e) {
            this.parseInputContent(false);
        },
        textBoxKeyDown: function(e){
            var keyCode = e.keyCode || e.which; 
            tabPressed = (keyCode==9) ? true : false;
            if (tabPressed) {
                // this.parseInputContent(true, true);
                this.closeCalendar();
            }
        },
        // calendarKeyDown: function(e){
        //     var keyCode = e.keyCode || e.which; 
        //     console.log(keyCode);
        //     // tabPressed = (keyCode==9) ? true : false;
        //     // if (tabPressed) {
        //     //     this.closeCalendar();
        //     // }
        // }

    };
    H.DatePick = DatePick;
    // H.addLoadEvent(function(){
    //     H.registerObject('date', DatePick);
    //     // H.registerObject('date', DatePick, new Date(1991, 06, 16));
    //     // H.registerObject('date', DatePick, null, new Date(2016, 01, 12), new Date(2016,01,27));
        
    // });

}(UXFrameHelper);

!function(H) {

    var Modal = function(element) {
        this.$m = element;
        this.$dimmer = document.getElementById("modal-dimmer");
    }

    Modal.selector = "div.modal";

    Modal.prototype = {

        toggle: function() {
            H.toggleClass(this.$m, 'modal-show');
            H.toggleClass(this.$dimmer, 'dimmer-active');
            H.toggleClass(document.body, 'modal-open');
        },

        headerCheck: function() {
            var header =  this.$m.querySelectorAll('div.modal-header')[0];
            var spacer =  this.$m.querySelectorAll('div.modal-spacer')[0];
            var content = this.$m.querySelectorAll('div.modal-content')[0];

            var header_margin_top = header.getBoundingClientRect().top;
            var header_margin_bottom = header.getBoundingClientRect().bottom;
            var header_size = header_margin_bottom - header_margin_top;

            var content_margin_top = content.getBoundingClientRect().top;
            if ( header_margin_top <=0 ) {
                if (!H.hasClass(header, 'sticky-header')) H.addClass(header, 'sticky-header');
                spacer.setAttribute('style','height:'+header_size+'px');
            }

            if ( content_margin_top > header_margin_bottom ) {
                H.removeClass(header, 'sticky-header');
                spacer.setAttribute('style','height:0px');
            }
        }
    }

    // Object.getPrototypeOf(H).Modal = function(a) {
    H.Modal = function(a) {
        return new Modal(H.elementOrId(a));
    }

    H.Register.Modal = function(e) {
        H.registerEvents(Modal.selector, 'scroll', Modal, 'headerCheck');
    }

    H.defaultRegister(H.Register.Modal);

}(UXFrameHelper);

!function(H) {
    var ScrollSpy = function(element) {
        this.$docOrWin = element;
        this.$scrollSpyUL = document.querySelectorAll('ul#spy-scroll-list')[0];
        this.$scrollSpyParent = document.querySelectorAll('li#spy-scroll-parent');
        this.$spiedElements = document.querySelectorAll('.spied');
        this.$parentLink = document.getElementById('spy-scroll-parent_link');
        this.$fixedPanel = document.getElementById('fixed-panel');
        // this.$liList_nodes = Array.prototype.slice.call(document.querySelectorAll('ul#spy-scroll-list li'));
        this.$liList_nodes = document.querySelectorAll('ul#spy-scroll-list li');
        this.$liList = [];
        for (var i = 0; i < this.$liList_nodes.length; i++) {
            this.$liList.push(this.$liList_nodes[i]);
        }
        this.$liList.unshift(this.$parentLink);
    }

    ScrollSpy.selector = "";

    ScrollSpy.prototype = {

        shouldBind: function() {
            return (this.$scrollSpyUL && this.$scrollSpyParent) ? true : false;
        },

        constructList: function() {
            for ( var i = 0 ? (this.$scrollSpyParent) : 1 ; i < this.$spiedElements.length; i++) {
                var a = document.createElement('a');
                var title = this.$spiedElements[i].id.replace("_"," ");
                a.appendChild(document.createTextNode(title));
                a.href="#"+this.$spiedElements[i].id;
                var li = document.createElement('li');
                li.appendChild(a);
                li.id = this.$spiedElements[i].id + "_link";
                this.$scrollSpyUL.appendChild(li);
            }
            H.addClass(this.$fixedPanel,'loaded');
            this.setActiveElement();
        },

        setActiveElement: function() {
            var active = -1;
            for ( var i = 0; i < this.$spiedElements.length; i++) {
                var top_margin = this.$spiedElements[i].getBoundingClientRect().top;
                if ( i == 0 && top_margin >= 0 ) {
                    active = this.$spiedElements[i];
                    break;
                } else {
                    if ( top_margin < 40 ) {
                        active = this.$spiedElements[i];
                    } else {
                        break;
                    }
                }
            }
            var act_li = document.querySelectorAll('#'+active.id+"_link")[0];
            if (act_li) {
                for ( var i = 0; i < this.$liList.length; i++ ) {
                    H.removeClass(this.$liList[i], 'selected');
                }
                if (act_li.className.indexOf(' selected') == -1){
                    H.addClass(act_li, 'selected');
                }
            }
        }
    }

    H.Register.ScrollSpy = function(e) {
        H.registerEvents(document, 'immediate', ScrollSpy, 'constructList');
        H.registerEvents(document, 'scroll', ScrollSpy, 'setActiveElement');
    }

    H.defaultRegister(H.Register.ScrollSpy);

}(UXFrameHelper);

!function(H) {
    var FixedPanel = function(element) {
        this.$docOrWin = element;
        this.$panel = document.querySelectorAll('#fixed-panel')[0];
        this.$spacer = document.querySelectorAll('#fixed-spacer')[0];
    }

    // FixedPanel.selector = "#fixed-panel";

    FixedPanel.prototype = {
        shouldBind: function() {
            return (this.$panel && this.$spacer) ? true : false;
        },
        setWidth: function() {
            this.$panel.style.width = this.$spacer.offsetWidth + "px";
        },
        setVerticalLocation: function() {
            if (this.$panel.getBoundingClientRect().top < 20) {
                H.addClass(this.$panel, 'active-fixed');
            }
            if (this.$spacer.getBoundingClientRect().top > 0) {
                H.removeClass(this.$panel, 'active-fixed');
            }
        },
        setInitial: function() {
            this.setWidth();
            this.setVerticalLocation();
        }
    }

    // Object.getPrototypeOf(H).FixedPanel = function(a) {
    H.FixedPanel = function(a) {
        return new FixedPanel(H.elementOrId(a));
    }

    H.Register.FixedPanel = function(e) {
        H.registerEvents(document, 'immediate', FixedPanel, 'setInitial', e);
        H.registerEvents(document, 'scroll', FixedPanel, 'setVerticalLocation', e);
        H.registerEvents(window, 'resize', FixedPanel, 'setWidth', e);
    }

    H.defaultRegister(H.Register.FixedPanel);


}(UXFrameHelper);


!function(H) {

    var LeftNav = function(element) {
        this.$e = element;
        this.$pn = element.parentElement;
    }

    LeftNav.selected = 'selected';
    LeftNav.selectedParent = 'selected-parent';

    LeftNav.prototype = {
        setSelected: function() {
            console.log(this);
            var chil = this.$pn.children;
            for (var i = 0; i < chil.length; i++) {
                H.removeClass(chil[i],LeftNav.selected);
                H.removeClass(chil[i],LeftNav.selectedParent);
            }
            H.addClass(this.$e,LeftNav.selected);
        }
    };

    H.Register.LeftNav = function(e) {
        H.registerEvents('.nav-left.interactive > ul > li', 'click', LeftNav, 'setSelected', e);
    }

    H.defaultRegister(H.Register.LeftNav);
    // H.registerEvents('.accordion .title', 'immediate', Accordion, 'setMaxHeight');
}(UXFrameHelper);

!function(H) {

    var DeepNav = function(element) {
        this.$e = element;
        this.$pe = element.parentElement;
    }

    DeepNav.selector = 'div.dn-title';

    DeepNav.prototype = {
        toggle: function() {
            var this_is_terminal = H.hasClass(this.$pe, 'dn-terminal');
            /* If this is terminal, all we need to do is remove the other actives. */
            if (this_is_terminal) {
                var siblings = this.$pe.parentElement.children;
                for (var i = 0; i < siblings.length; i++) {
                    H.removeClass(siblings[i], 'dn-active');
                }
                H.addClass(this.$pe, 'dn-active');
            }
            /* If not terminal, we have to make sure selected and actives are all set correctly */
            else {
                // We need to clear all neighbor children from being selected.
                var containingList = this.$pe.parentElement.children;
                for (var i = 0; i < containingList.length; i++) {
                    H.removeClass(containingList[i], 'dn-active');
                    H.removeClass(containingList[i], 'dn-selected');
                }
                var nextUp = this.$e;
                while (!H.hasClass(nextUp,'dn-top')) {
                    nextUp = nextUp.parentElement;
                    H.removeClass(nextUp, 'dn-active');
                    H.removeClass(nextUp, 'dn-selected');
                    if (H.hasClass(nextUp,'dn')) {
                        if (nextUp == this.$pe){
                            H.addClass(nextUp, 'dn-active');
                        } else {
                            H.addClass(nextUp, 'dn-selected');
                        }
                    }
                }
                // Finally, we need to clear all child dn's.
                var childLists = this.$pe.querySelectorAll('.dn');
                for ( var i = 0; i < childLists.length; i++ ){
                    H.removeClass(childLists[i], 'dn-active');
                    H.removeClass(childLists[i], 'dn-selected');
                }
            }
        }
    };

    // Object.getPrototypeOf(H).DeepNav = function(a) {
    H.DeepNav = function(a) {
        return new DeepNav(H.elementOrId(a));
    }

    H.Register.DeepNav = function(e) {
        H.registerEvents(DeepNav.selector, 'click', DeepNav, 'toggle', e);
    }

    H.defaultRegister(H.Register.DeepNav);

}(UXFrameHelper);

!function(H) {

    var CheckBox = function(element) {
        this.$e = element;
    }

    CheckBox.selector = 'input[name=checkall]';

    CheckBox.prototype = {
        setState: function(state) {
            if ( this.$e.type == 'checkbox' ) {
                this.$e.checked = state;
            }
        },

        toggleInsideElement: function(element_name) {
            var self = this;
            var x = this.$e;
            while ( x && x.nodeName.toLowerCase() !== element_name){
                x = x.parentNode;
            }
            // var checkboxes = Array.prototype.slice.call(x.querySelectorAll('input[type=checkbox]'));
            var checkboxes = x.querySelectorAll('input[type=checkbox]');
            for (var i = 0; i < checkboxes.length; i++) {
                new CheckBox(checkboxes[i]).setState(self.$e.checked);
            }
            // checkboxes.forEach(function(e, i, a){
            //     new CheckBox(e).setState(self.$e.checked);
            // });
        },

        toggleInsideTable: function() {
            this.toggleInsideElement('table');
        }
    };

    //Object.getPrototypeOf(H).CheckBox = function(a) {
    H.CheckBox = function(a) {
        return new CheckBox(H.elementOrId(a));
    }

    H.Register.CheckBox = function(e) {
        H.registerEvents(CheckBox.selector, 'click', CheckBox, 'toggleInsideTable');
    }

    H.defaultRegister(H.Register.CheckBox);

}(UXFrameHelper);

!function(H) {
    var DropDown = function(element) {
        this.$e = element;
        this.$dropdowns = document.querySelectorAll(DropDown.selector);
        this.$pn = element.parentElement;
    }

    DropDown.selector = '.dropdown';
    DropDown.menuSelector = '.dropdown-menu';

    DropDown.prototype = {

        close: function() {
            if (this.$pn && H.hasClass(this.$pn, 'opened')) {
                H.removeClass(this.$pn, 'opened');
            }
        },
        open: function() {
            if (this.$pn && !H.hasClass(this.$pn, 'opened')) {
                H.addClass(this.$pn, 'opened');
            }
        },
        closeAll: function(e) {
            // We don't want to clear stuff if the event is a right click.
            if ( e  && e.which == 3 ) return;

            for (var i = 0; i < this.$dropdowns.length; i++) {
                H.DropDown(this.$dropdowns[i]).close();
            }
        },
        toggle: function(event) {
            if (H.hasClass(this.$e, 'disabled')) return;

            if (event.stopPropagation){
                event.stopPropagation();
            }
            else if(window.event){
                window.event.cancelBubble=true;
            }

            var opened = H.hasClass(this.$pn, 'opened');

            H.DropDown(document).closeAll();


            if (!opened) {
                H.DropDown(this.$e).open();
            }

        },
        none: function(event) {
            // This simply exists to stop the click event from closing the
            // menu element when you click in it.
            if (event.stopPropagation){
                event.stopPropagation();
            }
            else if(window.event){
                window.event.cancelBubble=true;
            }
            return;

        }

    };

    // Object.getPrototypeOf(H).DropDown = function(a) {
    H.DropDown = function(a) {
        return new DropDown(H.elementOrId(a));
    }

    H.Register.DropDown = function(e) {
        H.registerEvents(document, 'click', DropDown, 'closeAll', e);
        H.registerEvents(DropDown.selector, 'click', DropDown, 'toggle', e);
        H.registerEvents(DropDown.menuSelector, 'click', DropDown, 'none', e);
    }

    H.defaultRegister(H.Register.DropDown);


}(UXFrameHelper);

!function(H) {

    var RadioCheckArea = function(e) {
        this.$e = e;
        this.$label = H.previousElementSibling(e);
        this.$checker = this.$label ? H.previousElementSibling(this.$label) : undefined;
    }

    RadioCheckArea.selector = "input:not(:disabled) ~ label ~ div";

    RadioCheckArea.prototype = {

        setCheck: function() {
            // console.log('set', this);
            // If the check is being set manually, we need to trigger this event.
            H.triggerEvent('change', this.$checker);
            if (this.$checker) {
                if (this.$checker.type == 'checkbox') {
                    this.$checker.checked = !this.$checker.checked;
                } else if (this.$checker.type == 'radio') {
                    this.$checker.checked = true;
                }
            }
        },
        // changed: function(e) {
        //     console.log('chekced,', e);
        // }
    }

    H.Register.RadioCheckArea = function(e) {
        H.registerEvents(RadioCheckArea.selector, 'click', RadioCheckArea, 'setCheck', e);
        // H.registerEvents('.active-ones input', 'change', RadioCheckArea, 'changed', e);
    }

    H.defaultRegister(H.Register.RadioCheckArea);

}(UXFrameHelper);

!function(H) {
    var FileUpload = function(element) {
        this.$e = element;
        this.$pe = element.parentElement;
    }

    FileUpload.selector = 'input[type=file]';

    FileUpload.prototype = {
        updateName: function(e) {
            var labs = this.$pe.querySelectorAll('.upload-name');
            for (var i=0; i < labs.length ; i++) {
                if (this.$e.value == "") {
                    labs[i].innerHTML = 'No file chosen';

                } else {
                    labs[i].innerHTML = this.$e.value.replace(/^.*\\/,"");

                }
            }
        },
        appendButton: function() {
            var btn = document.createElement('div');
            var t = document.createTextNode('Choose File');
            btn.appendChild(t);
            H.addClass(btn,'upload-button');
            H.addClass(btn,'button');
            H.addClass(btn,'');
            // H.addClass(btn,'small');

            var name = document.createElement('div');
            var t2 = document.createTextNode('No file chosen');
            name.appendChild(t2);
            H.addClass(name,'upload-name');

            var surlab = document.createElement('label');
            H.addClass(surlab,'upload-label');
            surlab.appendChild(this.$e);
            surlab.appendChild(btn);
            surlab.appendChild(name);
            this.$pe.appendChild(surlab);
        }

    }

    H.Register.FileUpload = function(e) {
        H.registerEvents(FileUpload.selector, 'change', FileUpload, 'updateName', e);
        H.registerEvents(FileUpload.selector, 'immediate', FileUpload, 'appendButton', e);
    }

    H.defaultRegister(H.Register.FileUpload);

}(UXFrameHelper);


!function(H) {
    var ThemeSwitcher = function(element) {
        if (!element) {
            this.$e = document.body;
        } else {
            this.$e = element;
        }
    }

    ThemeSwitcher.blackyellow = 'black-yellow';

    ThemeSwitcher.prototype = {
        toggle: function() {
            H.toggleClass(this.$e, ThemeSwitcher.blackyellow);
        },

        // Takes string 'green' or 'black'.
        switchTo: function(cls) {
            if (cls === 'black' && !H.hasClass(this.$e, ThemeSwitcher.blackyellow)) {
                H.addClass(this.$e, ThemeSwitcher.blackyellow);
            } else if ( cls === 'green' && H.hasClass(this.$e, ThemeSwitcher.blackyellow)) {
                H.removeClass(this.$e, ThemeSwitcher.blackyellow);
            }
        },

        selector: function(e) {
            var active = e.target;
            while (!H.hasClass(active.parentNode,'theme-list')) {
                active = active.parentNode;
            }
            if (H.hasClass(active,'green-yellow-selector')) {
                sessionStorage.setItem('themeswitched', 'green');
                H.ThemeSwitcher().switchTo('green');
            } else if (H.hasClass(active,'black-yellow-selector')) {
                sessionStorage.setItem('themeswitched', 'black');
                H.ThemeSwitcher().switchTo('black');
            }
        },

        setInitial: function() {
            if (sessionStorage && sessionStorage.getItem('themeswitched') == 'black') {
                H.addClass(document.body, 'black-yellow');
            }
        }


    }

    Object.getPrototypeOf(H).ThemeSwitcher = function(a) {
        return new ThemeSwitcher(H.elementOrId(a));
    }

    H.Register.ThemeSwitcher = function(e) {
        H.registerEvents(".theme-switcher-style li", 'click', ThemeSwitcher, "selector", e);
        H.registerEvents("body", 'immediate', ThemeSwitcher, "setInitial", e);
    }

    H.defaultRegister(H.Register.ThemeSwitcher);
    // H.registerEvents("li.black-yellow-selector", 'click', ThemeSwitcher, "selector");

}(UXFrameHelper);

!function(H) {
    var Charts = function(e){
        this.$element = e;
        this.$chart = 1;
    }

    Charts.replaceCircles = function(element) {
        // This is a very 'hacky' solution to these problems. It would be better
        // to do some custom work, but currently, this isn't a priority.
        d3.select(element).selectAll('.nv-series')[0].forEach(function(d,i) {
            var g = d3.select(d);
            // console.log(g.attr('transform'));
            var c = g.select('circle');
            var t = g.select('text');
            t.style('font-size', '15px');
            var color = c.style('fill');
            c.remove();
            g.append('rect')
                .attr('fill', color)
                .attr('height', 16)
                .attr('width', 16)
                .attr('x', -10)
                .attr('y', -9)
                .attr('rx', 2)
                .attr('ry', 2)
                ;
        });
    }

    Charts.colors = ["#367C2B", "#FFDE00", "#000000", 
                     "#c2d7bf", "#fff5b2", "#b2b2b2",
                     "#91B78B", "#ffed73", "#737373"];

    // Functions based off examples from nvd3 documentation.
    Charts.prototype = {

        Pie: function(data, options, callback) {
            var self = this;
            nv.addGraph(function() {
                var c;
                var c = nv.models.pieChart()
                    .x(function(d) { return d.label })
                    .y(function(d) { return d.value })
                    .showLabels(true)     //Display pie labels
                    .labelThreshold(0.01)  //Configure the minimum slice size for labels to show up
                    .labelType("percent") //Configure what type of data to show in the label. Can be "key", "value" or "percent"
                    .color(Charts.colors)
                    .donut(true)          //Turn on Donut mode. Makes pie chart look tasty!
                    .donutRatio(0.35)     //Configure how big you want the donut hole size to be.
                    .labelsOutside(true)
                    .startAngle(function(d) { return d.startAngle/1 -Math.PI/4 })
                    .endAngle(function(d) { return d.endAngle/1 -Math.PI/4 })
                    ;


                if (typeof options !== 'undefined' && options !== null) {
                    options.call(c);
                }

                c.legend.updateState(false);
                c.legend.padding(45);
                // c.legend.vers('furious')
                c.legend.margin({top: 5, right: 15, bottom: 5, left: 15});
                // c.legend.height(90);
                d3.select(self.$element)
                    .datum(data)
                    .transition().duration(350)
                    .call(c);

                Charts.replaceCircles(self.$element);

                c.update();

                nv.utils.windowResize(c.update);
                return c;
            }, function(c) {
                self.$chart = c;
                if (typeof callback !== 'undefined' && callback !== null) {
                    callback(c);
                }
                return self.$chart;
            });
            return self;
        }, 

        Bar: function(data, options, callback) {
            var self = this;
            nv.addGraph(function() {
                var c;
                var c = nv.models.discreteBarChart()
                    .x(function(d) { return d.label })    //Specify the data accessors.
                    .y(function(d) { return d.value })
                    .staggerLabels(false)    //Too many bars and not enough room? Try staggering labels.
                    // .tooltips(false)        //Don't show tooltips
                    .showValues(true)       //...instead, show the bar value right on top of each bar.
                    .duration(350)
                    .margin({ bottom: 25, left: 45})
                    .color(Charts.colors)
                    ;

                if (typeof options !== 'undefined' && options !== null) {
                    options.call(c);
                }

                c.tooltip.enabled(false);
                d3.select(self.$element)
                    .datum(data)
                    .transition().duration(350)
                    .call(c);

                c.update();
                nv.utils.windowResize(c.update);
                return c;
            }, function(c) {
                self.$chart = c;
                if (typeof callback !== 'undefined' && callback !== null) {
                    callback(c);
                }
                return self.$chart;
            });
            return self;
        },

        Line: function(data, options, callback) {
            var self = this;

            nv.addGraph(function() {
                var c;
                var c = nv.models.lineChart()
                    .useInteractiveGuideline(true)  //We want nice looking tooltips and a guideline!
                    .duration(350)  //how fast do you want the lines to transition?
                    .showLegend(true)       //Show the legend, allowing users to turn on/off line series.
                    .showYAxis(true)        //Show the y-axis
                    .showXAxis(true)        //Show the x-axis
                    .color(Charts.colors)
                    ;
                if (typeof options !== 'undefined' && options !== null) {
                    options.call(c);
                }

                c.legend.updateState(false);
                c.legend.padding(45);
                c.legend.margin({top: 5, right: 15, bottom: 15, left: 15});
                d3.select(self.$element)
                    .datum(data)
                    .transition().duration(350)
                    .call(c);
                Charts.replaceCircles(self.$element);

                c.update();
                nv.utils.windowResize(c.update);
                return c;
            }, function(c) {
                self.$chart = c;
                if (typeof callback !== 'undefined' && callback !== null) {
                    callback(c);
                }
                return self.$chart;
            });
            return self;

        }


    }

    if (typeof nv !== 'undefined' && typeof d3 !== 'undefined') {
        H.Charts = function(a) {
            return new Charts(H.elementOrId(a));
        }
    }


}(UXFrameHelper);
