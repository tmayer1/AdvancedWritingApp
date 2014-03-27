/*
    This file is part of Advanced Writing App.
  
    Advanced Writing App is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Advanced Writing App is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Advanced Writing App.  If not, see <http://www.gnu.org/licenses/>.
 */

function fadeinfunction(elementID) {
    setTimeout(function() {
        $(elementID).fadeIn();
    }, 10);
}

function fadeoutfunction(elementID) {
    setTimeout(function() {
        $(elementID).fadeOut();
    }, 10);
}

function fadetoggle(elementID) {
    $(elementID).fadeToggle(600);
}

function setposnr(elementid, new_value) {

    document.getElementById(elementid).value = new_value;
}