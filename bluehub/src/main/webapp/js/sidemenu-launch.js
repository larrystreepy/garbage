$(function() {
                //graceful degradation
                $('#ui_element').find('ul').css({
                	 
                }).siblings('.mh_right').css({
                	
                });
				
                var $arrow = $('#ui_element').find('.mh_right');
                var $menu  = $('#ui_element').find('ul');
                $arrow.bind('mouseenter',function(){
                    var $this 	= $(this);
                    $this.stop().animate({'left':'165px'},50);
                    $menu.stop().animate({'left':'202px'},500,function(){
                        $(this).find('a')
                        .unbind('mouseenter,mouseleave')
                        .bind('mouseenter',function(){
                            $(this).addClass('hover');
                        })
                        .bind('mouseleave',function(){
                            $(this).removeClass('hover');
                        });
                    });
                });
                $menu.bind('mouseleave',function(){
                    var $this 	= $(this);
                    $this.stop().animate({'left':'-660px'},500,function(){
                        $arrow.stop().animate({'left':'175px'},500);
                    });
                });
            });