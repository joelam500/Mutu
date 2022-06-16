<h2>Details</h2>

<div>
    <dl class="dl-horizontal">
        <dt>Order Id</dt>
        <dd><c:out value="${orders.oid}" /></dd>

        <dt>Stocks</dt>
        <dd><c:out value="${orders.sid.sname}" /> (<c:out value="${orders.sid.scode}" />)</dd>

        <dt>Buy / Sell</dt>
        <dd><c:out value="${orders.isbuy ? 'Buy' : 'Sell'}" /></dd>

        <dt>Quantity</dt>
        <dd><c:out value="${orders.qty}" /></dd>

        <dt>Price</dt>
        <dd><c:out value="${orders.price}" /></dd>
    </dl>
</div>
<p>
    <a href="<c:url value="/orders/edit/${orders.oid}"/>" class="btn btn-warning">Edit</a> |
    <a href="<c:url value="/orders/index" />" class="btn btn-primary">Back</a>
</p>
