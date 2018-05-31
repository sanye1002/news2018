<div class="margin-top-30 text-align-right">
    <div class="next">
        <ul class="pagination">
            <li><a href="${url}?page=1&size=${size}">首页</a></li>
                <#if currentPage lte 1>
                    <li class="disabled"><a>上一页</a></li>
                <#else>
                    <li><a href="${url}?page=${currentPage-1}&size=${size}">上一页</a></li>

                </#if>

           <#list 1..pageContent.getTotalPages() as index>
                <#if currentPage == index >
                     <li class="active"><a href="#">${index}</a></li>
                <#else>
                    <li><a href="${url}?page=${index}&size=${size}">${index}</a></li>
                </#if>
            </#list>
            <#if currentPage gte pageContent.getTotalPages()>
                <li class="disabled"><a>下一页</a></li>
            <#else>
                <li><a href="${url}?page=${currentPage+1}&size=${size}">下一页</a></li>
            </#if>
            <li>
                <a href="${url}?page=${pageContent.getTotalPages()}">尾页</a>
            </li>
        </ul>
    </div>
</div>
