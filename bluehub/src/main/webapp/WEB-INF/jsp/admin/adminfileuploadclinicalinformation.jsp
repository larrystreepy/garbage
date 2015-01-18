
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../headerbluehub.jsp"></jsp:include>




<script>
var contextPath = "<%=request.getContextPath()%>";
</script>

<div class="content-body">


<div id="panel-typeahead" class="panel panel-default sortable-widget-item">
                        
                        <div class="panel-body">
                           
                            <div id="panel-dropzone" class="panel panel-default sortable-widget-item">
                        <div class="panel-heading">
                            <div class="panel-actions">
                                <button data-refresh="#panel-dropzone" title="refresh" class="btn-panel">
                                    <i class="fa fa-refresh"></i>
                                </button>
                                <button data-expand="#panel-dropzone" title="expand" class="btn-panel">
                                    <i class="fa fa-expand"></i>
                                </button>
                                <button data-collapse="#panel-dropzone" title="collapse" class="btn-panel">
                                    <i class="fa fa-caret-down"></i>
                                </button>
                                <button data-close="#panel-dropzone" title="close" class="btn-panel">
                                    <i class="fa fa-times"></i>
                                </button>
                            </div>
                            <h3 class="panel-title">Dropzone</h3>
                        </div>

                        <form action="<%=request.getContextPath()%>/uploadfile.do" data-input="dropzone" class="dropzone" id="dropBox" name="dropBox" enctype="multipart/form-data" >
                            <div class="fallback">
                                <input name="mydropzone" id="mydropzone" type="file" multiple />
                            </div>
                        </form>
                    </div>
                            
                            
                            
                        </div>
                    </div>
                
			
			
			
			
			
			
			
			
			
                </div>
                
                
                
                
                <jsp:include page="../footerbluehub.jsp"></jsp:include>