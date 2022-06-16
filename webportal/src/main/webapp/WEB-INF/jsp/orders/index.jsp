<link rel="stylesheet" href="<c:url value="/css/jquery.dataTables.min.css" />">
<script src="<c:url value="/js/jquery.dataTables.min.js" />"></script>

<h2>Transactions</h2>
<hr/>

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
        $('#stock-list').DataTable();
    });
</script>
