<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    <div class="mb-1">
    ${message!}
    </div>
    <@l.login "/login" false/>
</@c.page>