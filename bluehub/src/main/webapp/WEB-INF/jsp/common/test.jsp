<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Test</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="stilearning">

        <meta http-equiv="x-pjax-version" content="v173">

        <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
        <!-- fav and touch icons -->
        
        
       
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script> 
<link type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/themes/south-street/jquery-ui.css" rel="stylesheet"> 
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/jquery-ui.min.js"></script>



<script type="text/javascript" src="js/jquery.ui.touch-punch.min.js"></script>


<link type="text/css" href="css/jquery.signature.css" rel="stylesheet"> 
<script type="text/javascript" src="js/jquery.signature.js"></script>
  
        <script type="text/javascript">


        $('#defaultSignature').signature(); 
        
        $('#removeSignature').toggle(function() { 
                $(this).text('Re-attach'); 
                $('#defaultSignature').signature('destroy'); 
            }, 
            function() { 
                $(this).text('Remove'); 
                $('#defaultSignature').signature(); 
            }); 
         
        $('#disableSignature').toggle(function() { 
                $(this).text('Enable'); 
                $('#defaultSignature').signature('disable'); 
            }, 
            function() { 
                $(this).text('Disable'); 
                $('#defaultSignature').signature('enable'); 
            });

        

        </script>
        
       
      
       
       
    </head>

    <body class="animated fadeIn">
        <!-- section header -->
        <header class="header">
            <!-- header brand -->
            <div class="header-brand">
                <a data-pjax=".content-body" href="" style="font-size:34px;">
                 	Blue Hub
                </a>
            </div><!-- header brand -->
            
            
            
            
            <ul class="hidden-xs header-menu pull-right">
          
            </ul><!--/header-menu pull-right-->
        </header><!--/header-->

        
        <!-- content section -->
        <section class="section">
            
            <aside class="side-left">
              
            </aside><!--/side-left-->

            <div class="content">

            
            
            
            
                              
              
                

                <div class="content-body">
                
<!--                  <div id="panel-fvalidate2" class="panel panel-default sortable-widget-item"> -->
                 
				  
                   
					
					
<!-- 					</div> -->
                </div><!--/content-body -->
                
              
              

</div><!--/content -->

        </section><!--/content section -->
        

        <!-- section footer -->
        <!-- <a rel="to-top" href="#top"><i class="fa fa-arrow-up"></i></a> -->
        <footer>
            <p>&copy; Copyright 2014 - 2015 Blue Hub, All Rights Reserved</p>
        </footer>




	
    </body>
</html>
