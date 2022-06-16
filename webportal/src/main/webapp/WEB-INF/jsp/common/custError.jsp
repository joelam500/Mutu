<style>
    .error-page{
        width: 600px;
        margin: 20px auto 0 auto;
    }
    .text-yellow {
        color: #f39c12 !important;
    }
    .error-page > .error-content{
        display: block;
    }
</style>

<section class="content">
    <div class="error-page">
        <div class="error-content">
            <h3>Oops! Incorrect operation</h3>

            <p>
                <c:out value="${errMsg}" />
                Meanwhile, you may <a href="<c:url value="${rtnPath}" />" >return</a> to get back previous page
            </p>
        </div>
    </div>
</section>