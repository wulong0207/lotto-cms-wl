'use strict';

// Add ECMA262-5 method binding if not supported natively
//
if (!('bind' in Function.prototype)) {
    Function.prototype.bind= function(owner) {
        var that= this;
        if (arguments.length<=1) {
            return function() {
                return that.apply(owner, arguments);
            };
        } else {
            var args= Array.prototype.slice.call(arguments, 1);
            return function() {
                return that.apply(owner, arguments.length===0? args : args.concat(Array.prototype.slice.call(arguments)));
            };
        }
    };
}

// Add ECMA262-5 string trim if not supported natively
//
if (!('trim' in String.prototype)) {
    String.prototype.trim= function() {
        return this.replace(/^\s+/, '').replace(/\s+$/, '');
    };
}

// Add ECMA262-5 Array methods if not supported natively
//
if (!('indexOf' in Array.prototype)) {
    Array.prototype.indexOf= function(find, i /*opt*/) {
        if (i===undefined) i= 0;
        if (i<0) i+= this.length;
        if (i<0) i= 0;
        for (var n= this.length; i<n; i++)
            if (i in this && this[i]===find)
                return i;
        return -1;
    };
}
if (!('lastIndexOf' in Array.prototype)) {
    Array.prototype.lastIndexOf= function(find, i /*opt*/) {
        if (i===undefined) i= this.length-1;
        if (i<0) i+= this.length;
        if (i>this.length-1) i= this.length-1;
        for (i++; i-->0;) /* i++ because from-argument is sadly inclusive */
            if (i in this && this[i]===find)
                return i;
        return -1;
    };
}
if (!('forEach' in Array.prototype)) {
    Array.prototype.forEach= function(action, that /*opt*/) {
        for (var i= 0, n= this.length; i<n; i++)
            if (i in this)
                action.call(that, this[i], i, this);
    };
}
if (!('map' in Array.prototype)) {
    Array.prototype.map= function(mapper, that /*opt*/) {
        var other= new Array(this.length);
        for (var i= 0, n= this.length; i<n; i++)
            if (i in this)
                other[i]= mapper.call(that, this[i], i, this);
        return other;
    };
}
if (!('filter' in Array.prototype)) {
    Array.prototype.filter= function(filter, that /*opt*/) {
        var other= [], v;
        for (var i=0, n= this.length; i<n; i++)
            if (i in this && filter.call(that, v= this[i], i, this))
                other.push(v);
        return other;
    };
}
if (!('every' in Array.prototype)) {
    Array.prototype.every= function(tester, that /*opt*/) {
        for (var i= 0, n= this.length; i<n; i++)
            if (i in this && !tester.call(that, this[i], i, this))
                return false;
        return true;
    };
}
if (!('some' in Array.prototype)) {
    Array.prototype.some= function(tester, that /*opt*/) {
        for (var i= 0, n= this.length; i<n; i++)
            if (i in this && tester.call(that, this[i], i, this))
                return true;
        return false;
    };
}

if (!Array.prototype.fill) {
  Array.prototype.fill = function(value) {

    // Steps 1-2.
    if (this == null) {
      throw new TypeError('this is null or not defined');
    }

    var O = Object(this);

    // Steps 3-5.
    var len = O.length >>> 0;

    // Steps 6-7.
    var start = arguments[1];
    var relativeStart = start >> 0;

    // Step 8.
    var k = relativeStart < 0 ?
      Math.max(len + relativeStart, 0) :
      Math.min(relativeStart, len);

    // Steps 9-10.
    var end = arguments[2];
    var relativeEnd = end === undefined ?
      len : end >> 0;

    // Step 11.
    var final = relativeEnd < 0 ?
      Math.max(len + relativeEnd, 0) :
      Math.min(relativeEnd, len);

    // Step 12.
    while (k < final) {
      O[k] = value;
      k++;
    }

    // Step 13.
    return O;
  };
}

//Production steps of ECMA-262, Edition 5, 15.4.4.21
//Reference: http://es5.github.io/#x15.4.4.21
//https://tc39.github.io/ecma262/#sec-array.prototype.reduce
if (!Array.prototype.reduce) {
Object.defineProperty(Array.prototype, 'reduce', {
 value: function(callback /*, initialValue*/) {
   if (this === null) {
     throw new TypeError( 'Array.prototype.reduce ' + 
       'called on null or undefined' );
   }
   if (typeof callback !== 'function') {
     throw new TypeError( callback +
       ' is not a function');
   }

   // 1. Let O be ? ToObject(this value).
   var o = Object(this);

   // 2. Let len be ? ToLength(? Get(O, "length")).
   var len = o.length >>> 0; 

   // Steps 3, 4, 5, 6, 7      
   var k = 0; 
   var value;

   if (arguments.length >= 2) {
     value = arguments[1];
   } else {
     while (k < len && !(k in o)) {
       k++; 
     }

     // 3. If len is 0 and initialValue is not present,
     //    throw a TypeError exception.
     if (k >= len) {
       throw new TypeError( 'Reduce of empty array ' +
         'with no initial value' );
     }
     value = o[k++];
   }

   // 8. Repeat, while k < len
   while (k < len) {
     // a. Let Pk be ! ToString(k).
     // b. Let kPresent be ? HasProperty(O, Pk).
     // c. If kPresent is true, then
     //    i.  Let kValue be ? Get(O, Pk).
     //    ii. Let accumulator be ? Call(
     //          callbackfn, undefined,
     //          « accumulator, kValue, k, O »).
     if (k in o) {
       value = callback(value, o[k], k, o);
     }

     // d. Increase k by 1.      
     k++;
   }

   // 9. Return accumulator.
   return value;
 }
});
}
//Other ECMA262-5 methods not implemented here include Array reduce/reduceRight, 
//the JSON ones and the few new Object methods that can be reliably implemented as JS functions.