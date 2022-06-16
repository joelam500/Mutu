<link rel="stylesheet" href="<c:url value="/css/jquery.dataTables.min.css" />">
<script src="<c:url value="/js/jquery.dataTables.min.js" />"></script>

<div class="table-responsive">
    <table id="stock-list" class="display" class="table table-striped ">
        <thead>
            <tr>
                <th scope="col">Code</th>
                <th scope="col">Name</th>
                <th scope="col">Current Price (HK$)</th>
                <th scope="col">Turnover (HK$)</th>
                <th scope="col">&nbsp;</th>
            </tr>
        </thead>
        <tbody>
            <%-- 
                1. Loop the content of stocks list send from controller 
                Map to corresponding field with the column header
            --%>
            <c:forEach var="stocks" items="${allStocks}">
                <tr>
                    <th scope="row">
                        <a href="<c:url value="/stocks/details/${stocks.sid}" />">
                            <c:out value="${stocks.scode}" />
                        </a>			
                    </th>
                    <td>
                        <c:out value="${stocks.sname}" />
                    </td>
                    <td>
                        <c:out value="${stocks.scurrentprice}" />
                    </td>
                    <td>
                        <c:out value="${stocks.turnover}" />
                    </td>                    
                    <td>&nbsp;</td>
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