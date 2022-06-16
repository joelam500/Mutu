<!DOCTYPE html>
<html>
    <head>
        <title>MuTu Sheep Sheep</title>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css" />">
        <script src="<c:url value="/js/jquery.min.js" />"></script>
        <script src="<c:url value="/js/bootstrap.min.js" />"></script>
        <script src="<c:url value="/js/webportal.js" />"></script>
    </head>
    <body>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
              <div class="navbar-header">
                  <a class="navbar-brand" href="<c:url value="/" />">MuTu Sheep Sheep</a>
              </div>
              <ul class="nav navbar-nav">
                <li><a href="<c:url value="/stocks/index" />">Stocks</a></li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        Orders
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value="/orders/unsettle" />">Opening Orders List</a></li>
                        <li><a href="<c:url value="/orders/create" />">Place Order</a></li>
                        <li><a href="<c:url value="/orders/index" />">Transactions</a></li>
                    </ul>
                </li>              
              </ul>
            </div>
        </nav>

        <div class="container">
            <div class="row" style="margin-top: 5px;"> </div>
            <div class="row">
                <div class="col-md-12">
                    <jsp:include page="${content}" />
                </div>
            </div>
        </div>
                
        <!-- Modal -->
        <div class="modal fade" id="delBox" tabindex="-1" role="dialog" aria-labelledby="delBoxTrigger" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="delBoxTitle">Confirmation</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        Are you sure to delete <span id="delBoxObjectName"></span>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-danger" id="delBoxConfirm">Delete</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- end of Modal -->
    </body>
</html>