var flagup=true;
var flagSub=true;
var countval=0;
var get=true;
var t=0;
var obj=true;

//setInterval(function(){myTimer()},1000);


	function methodpro(){

	//graceful degradation
    $('#ui_element').find('ul').css({
    	 'left'	:	'-660px'
    }).siblings('.mh_right').css({
    	'left'	:	'175px'
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
        if(flagup==true){
        	 $this.stop().animate({'left':'-660px'},500,function(){
                 $arrow.stop().animate({'left':'175px'},500);
             });
        }
    });

	
	}


$(function() {
	//graceful degradation
    $('#ui_element').find('ul').css({
    	 'left'	:	'-660px'
    }).siblings('.mh_right').css({
    	'left'	:	'175px'
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
     
        if(flagup==true){
        	
        	 $this.stop().animate({'left':'-660px'},500,function(){
                 $arrow.stop().animate({'left':'175px'},500);
                
             });
        }else{
        	countval++;
        }
       
   
       if(countval==2){
     	   methodpro();
     	   
        }
      
    });
  
 });



function setFlag(){
	flagup=false;
}
function flagUp(){
	flagup=true;
	countval=0;
}
function changeFlag(){
	flagSub=false;
}


