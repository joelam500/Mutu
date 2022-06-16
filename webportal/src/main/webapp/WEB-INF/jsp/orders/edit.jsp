<h2>Edit Orders</h2>

<form:form method="post" modelAttribute="editOrders">
    <form:hidden path="oid" />
    <div class="form-horizontal">
        <div class="form-group">
            <form:label path="scode" cssClass="control-label col-md-2">
                Stock Code
            </form:label>
            <div class="col-md-10">
                <form:input type="text" class="form-control" path="scode" />
                <form:errors path="scode" cssClass="text-danger" />
            </div>
        </div>

        <div class="form-group">
            <div class="form-check row">
                <form:label cssClass="control-label col-md-2" path="isbuy">
                    Buy
                </form:label>
                <form:radiobutton cssClass="form-check-input col-md-1" path="isbuy" value="true" />
            </div>

            <div class="form-check row">
                <form:label cssClass="control-label col-md-2" path="isbuy">
                    Sell
                </form:label>
                <form:radiobutton cssClass="form-check-input col-md-1" path="isbuy" value="false" />
            </div>
        </div>

        <div class="form-group">
            <form:label path="qty" cssClass="control-label col-md-2">
                Quantity
            </form:label>
            <div class="col-md-10">
                <form:input type="text" cssClass="form-control" path="qty" />
                <form:errors path="qty" cssClass="text-danger" />
            </div>
        </div>

        <div class="form-group">
            <form:label path="price" cssClass="control-label col-md-2">
                Price
            </form:label>
            <div class="col-md-10">
                <form:input type="text" cssClass="form-control" path="price" />
                <form:errors path="price" cssClass="text-danger" />
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-offset-2 col-md-10">
                <input type="submit" value="Update" class="btn btn-primary" />
            </div>
        </div>
    </div>
</form:form>
<div>
    <a href="<c:url value="/orders/unsettle" />" class="btn btn-warning" >Back</a>
</div>