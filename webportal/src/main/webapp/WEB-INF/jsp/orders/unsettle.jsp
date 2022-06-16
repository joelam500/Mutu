<link rel="stylesheet" href="<c:url value="/css/jquery.dataTables.min.css" />">
<script src="<c:url value="/js/jquery.dataTables.min.js" />"></script>

<h2>Opening orders</h2>
<hr/>

<div class="table-responsive">
    <table id="stock-list" class="display" class="table table-striped ">
        <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Stock</th>
                <th scope="col">Buy / Sell</th>
                <th scope="col">Qty</th>
                <th scope="col">Price (HK$)</th>
                <th scope="col">&nbsp;</th>
            </tr>
        </thead>
        <tbody>
            <%-- 
                1. Loop the content of orders list send from controller 
                Map to corresponding field with the column header
            --%>
            <c:forEach var="orders" items="${allOrders}">
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
                        <c:out value="${orders.isbuy ? 'Buy' : 'Sell'}" />
                    </td>                    
                    <td>
                        <c:out value="${orders.qty}" />
                    </td>
                    <td>
                        <c:out value="${orders.price}" />
                    </td>                    
                    <td>
                        <a href="<c:url value="/orders/edit/${orders.oid}" /> " class="btn btn-warning">Edit</a>
                        &nbsp;&nbsp; | &nbsp;&nbsp; 
                        <a href="#" class="offset-md-1 btn btn-danger" onclick="confirmDel('[ID : ${orders.oid}]', '<c:url value="/orders/delete/${orders.oid}" />')">Delete</a>
                    </td>
                </tr>
            </c:forEach>  
        </tbody>
    </table>
</div>

<script>
    $(document).ready(function() {
        $('#stock-list').DataTable();
    });
</script>