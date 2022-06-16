<h2>Details of <c:out value="${stocksObj.sname}" /></h2>

<div class="row">
    <div class="col-md-12">
        <h4><c:out value="${stocksObj.sname}" /></h4>
        <hr />
        <dl class="dl-horizontal">
            <dt>Code</dt>
            <dd><c:out value="${stocksObj.scode}" /></dd>
            <dt>Current Price</dt>
            <dd><c:out value="${stocksObj.scurrentprice}" /></dd>
            <dt>Turnover</dt>
            <dd><c:out value="${stocksObj.turnover}" /></dd>        
        </dl>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <link rel="stylesheet" href="<c:url value="/css/jquery.dataTables.min.css" />">
        <script src="<c:url value="/js/jquery.dataTables.min.js" />"></script>

        <div class="table-responsive">
            <table id="stock-list" class="display" class="table table-striped ">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Stock</th>
                        <th scope="col">Qty</th>
                        <th scope="col">Price (HK$)</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- 
                        1. Loop the content of orders list send from controller 
                        Map to corresponding field with the column header
                    --%>
                    <c:forEach var="orders" items="${stocksObj.ordersList}">
                        <tr>
                            <th scope="row">
                                <a href="<c:url value="/orders/details/${orders.oid}" />">
                                    <c:out value="${orders.oid}" />
                                </a>			
                            </th>
                            <td>
                                <c:out value="${orders.sid.sname}" /> (<c:out value="${orders.sid.scode}" />)
                            </td>                   
                            <td>
                                <c:out value="${orders.qty}" />
                            </td>
                            <td>
                                <c:out value="${orders.price}" />
                            </td>
                        </tr>
                    </c:forEach>  
                </tbody>
            </table>
        </div>

        <script>
            $(document).ready(function() {
                $('#stock-list').DataTable({searching: false, paging: false, info: false});
            });
        </script>
    </div>
</div>
<div class="row" style="margin-top: 10px;">
    <div class="col-md-12">
        <p>
            <a href="<c:url value="/stocks" />" class="btn btn-primary">Back</a>
        </p>
    </div>
</div>