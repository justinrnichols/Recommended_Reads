(function ($) {
    $.fn.fontstar = function (options, callBackFunction) {
        $.fn.fontstar.callBackFunction = callBackFunction || function () {}
        $.fn.fontstar.defaults = $.extend({}, $.fn.fontstar.defaults, options);
        $.each(this, function (i) {
            $.fn.fontstar.star.init(this);
        });
    };
    $.fn.fontstar.defaults = {
        icon: "far fa-star fa-2x",
        iconfull: "fas fa-star fa-2x",
        hovercolor: "#000000",
        starcolor: "#000000",
        selectable: true
    };
    $.fn.fontstar.star = {
        ul: [],
        li: [],
        selectValue: [],
        init: function (self) {
            var ul = $("<ul>").addClass("fontstar");
            ul.addClass('d-flex justify-content-center mt-4');
            $(self).after(ul);
            var me = this;
            var length = this.li.length;
            me.selectValue[length] = $(self).val();
            this.li[length] = [];
            $(self).find('option').each(function () {
                var lit = $('<li>').html('');
                if ($(this).val() == '0' || $(this).val() == '6') {
                    lit.attr('style', 'opacity: 0.0;');
                }
                lit.attr('class', $.fn.fontstar.defaults.icon);
                lit.css('color', $.fn.fontstar.defaults.starcolor);
                lit.css({
                    'list-style': 'none',
                    'float': 'left',
                    'padding': '0 2px'
                });
                if ($.fn.fontstar.defaults.selectable) {
                    lit.css('cursor', 'pointer');
                }else{
                    lit.css('cursor', 'default');
                }
                lit.attr('data-value', $(this).val());
                lit.attr('data-text', $(this).text());
                lit.appendTo(ul);
                me.li[length].push(lit);
            });
            var me = this;
            $.each(me.li, function (index, value) {
                if ($.fn.fontstar.defaults.selectable) {
                    $.each(this, function () {
                        $(this).click(function () {
                            me.redrawStar($(this).attr('data-text'), me.li);
                            $(self).val($(this).attr('data-value'));
                            me.selectValue[index] = $(this).attr('data-value');
                            $.fn.fontstar.callBackFunction(me.selectValue[index], self);
                        });
                        $(this).mouseenter(function () {
                            var loopEnded = false;
                            var value = $(this).attr('data-text');
                            $.each(me.li[index], function (i, val) {
                                if (!loopEnded) {
                                    $(this).css('color', $.fn.fontstar.defaults.hovercolor);
                                    $(this).attr('class', $.fn.fontstar.defaults.iconfull);
                                } else {
                                    $(this).css('color', $.fn.fontstar.defaults.starcolor);
                                    $(this).attr('class', $.fn.fontstar.defaults.icon);
                                }
                                if (value == $(this).attr('data-text')) {
                                    loopEnded = true;
                                }
                            });
                        });
                        $(this).mouseout(function () {
                            var loopEnded = false;

                            $.each(me.li[index], function (i, val) {

                                if (!loopEnded) {
                                    $(this).css('color', $.fn.fontstar.defaults.hovercolor);
                                    $(this).attr('class', $.fn.fontstar.defaults.iconfull);
                                } else {
                                    $(this).css('color', $.fn.fontstar.defaults.starcolor);
                                    $(this).attr('class', $.fn.fontstar.defaults.icon);
                                }
                                if (me.selectValue[index] == $(this).attr('data-text')) {
                                    loopEnded = true;
                                }
                            });
                        });
                    });
                }
                if (me.selectValue[index] && me.selectValue[index] != "") {
                    me.redrawStar(me.selectValue[index], me.li[index]);
                }
            });
            $(self).hide();
            return this;
        },
        redrawStar: function (value, li) {
            var loopEnded = false;
            $.each(li, function (i, val) {
                if (!loopEnded) {
                    $(this).css('color', $.fn.fontstar.defaults.hovercolor);
                    $(this).attr('class', $.fn.fontstar.defaults.iconfull);
                } else {
                    $(this).css('color', $.fn.fontstar.defaults.starcolor);
                    $(this).attr('class', $.fn.fontstar.defaults.icon);
                }
                if (value == $(this).attr('data-text')) {
                    loopEnded = true;
                }
            });
        }
    }
}(jQuery));