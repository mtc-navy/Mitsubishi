//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props:{item:{}},
    template:[
        '<li>',
        '	<a v-if="item.type === 0" href="javascript:;">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
        '		<span>{{item.name}}</span>',
        '		<i class="fa fa-angle-left pull-right"></i>',
        '	</a>',
        '	<ul v-if="item.type === 0" class="treeview-menu">',
        '		<menu-item :item="item" v-for="item in item.list"></menu-item>',
        '	</ul>',

        '	<a v-if="item.type === 1 && item.parentId === 0" :href="\'#\'+item.url">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
        '		<span>{{item.name}}</span>',
        '	</a>',

        '	<a v-if="item.type === 1 && item.parentId != 0" :href="\'#\'+item.url" @click="reload(item)"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
        '</li>'
    ].join(''),
    methods:{
        reload:function(item){
            if(vm.editableTabs2Map[item.url] == null){
                vm.addTab({
                    content:item.url,
                    title:item.name
                });
            }else{
                vm.editableTabsValue2 = item.url;
            }

            /*if(location.href.indexOf(item.url)>0){
                document.getElementById("frame").contentWindow.location.href="/"+item.url;
            }*/
        }
    }
});

//iframe自适应
$(window).on('resize', function() {
    var $content = $('.content');
    $content.height($(this).height() - 140);
    $content.find('iframe').each(function() {
        $(this).height($content.height());
    });
}).resize();

//注册菜单组件
Vue.component('menuItem',menuItem);

var vm = new Vue({
    el:'#rrapp',
    data:{
        user:{},
        menuList:{},
        menuMap:{},
        main:"main.html",
        password:'',
        newPassword:'',
        navTitle:"控制台",

        editableTabsValue2:null,
        editableTabs2: [],
        editableTabs2Map:{},
    },
    methods: {
        getMenuList: function (callback) {
            $.getJSON("sys/menu/nav?_"+$.now(), function(r){
                vm.menuList = r.menuList;
                if(typeof callback != 'undefined'){
                    callback();
                }
            });
        },
        getUser: function(){
            $.getJSON("sys/user/info?_"+$.now(), function(r){
                vm.user = r.user;
            });
        },
        updatePassword: function(){
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: "修改密码",
                area: ['550px', '270px'],
                shadeClose: false,
                content: jQuery("#passwordLayer"),
                btn: ['修改','取消'],
                btn1: function (index) {
                    var data = "password="+vm.password+"&newPassword="+vm.newPassword;
                    $.ajax({
                        type: "POST",
                        url: "sys/user/password",
                        data: data,
                        dataType: "json",
                        success: function(result){
                            if(result.code == 0){
                                layer.close(index);
                                layer.alert('修改成功', function(index){
                                    location.reload();
                                });
                            }else{
                                layer.alert(result.msg);
                            }
                        }
                    });
                }
            });
        },
        addTab(tab) {
            this.editableTabs2.push({
                title: tab.title,
                name: tab.content,
                content: tab.content,
            });
            this.editableTabsValue2 = tab.content;
            this.editableTabs2Map[tab.content] = tab;
        },
        removeTab(targetName) {
            let tabs = this.editableTabs2;
            let activeName = this.editableTabsValue2;
            if (activeName === targetName) {
                tabs.forEach((tab, index) => {
                    if (tab.name === targetName) {
                    let nextTab = tabs[index + 1] || tabs[index - 1];
                    if (nextTab) {
                        activeName = nextTab.name;
                    }
                }
            });
            }
            delete vm.editableTabs2Map[targetName];
            this.editableTabsValue2 = activeName;
            this.editableTabs2 = tabs.filter(tab => tab.name !== targetName);
            vm.chooseMenu('#'+activeName);
        },
        clickTab(obj){
            vm.chooseMenu('#'+obj.name);
            window.location.hash = '#'+obj.name;
        },
        chooseMenu(url){
            url = decodeURI(url);
            if(url.indexOf(vm.editableTabsValue2) > 0){
                $(".sidebar-menu li").removeClass("active");
                $(".treeview-menu li").removeClass("active");

                var lis =  $("a[href='"+decodeURI(url)+"']").parents("li");
                lis.addClass("active");
                $(".treeview-menu").attr("style","display:none");
                lis.each(function(){
                    $(this).parents("ul").attr("style","display:block;");
                });

                vm.navTitle = $("a[href='"+url+"']").text();
            }
        }
    },
    created: function(){
        this.getUser();
    },
    mounted: function(){
        this.getMenuList(function(){
            var router = new Router();
            routerList(router, vm.menuList);
            var url = window.location.href;
            var content = 'main.html';
            var title = '首页';
            if(url.indexOf('#') > 0){
                var subUrl = url.substr(url.indexOf('#')+1,url.length);
                content = decodeURI(subUrl);
                if(vm.menuMap[content] != null){
                    title = vm.menuMap[content].name;
                }
            }
            vm.addTab({content:content,title:title})
        });
    },
    updated: function(){
        //路由
        var router = new Router();
        routerList(router, vm.menuList);
        router.start();
    }
});



function routerList(router, menuList){
    for(var key in menuList){
        var menu = menuList[key];
        if(menu.type == 0){
            routerList(router, menu.list);
        }else if(menu.type == 1){
            vm.menuMap[menu.url] = menu;
            router.add('#'+menu.url, function() {
                var url = window.location.hash;
                //导航菜单展开
                vm.chooseMenu(url);
            });
        }
    }
}

function replaceAll (str,FindText, RepText) {
    var regExp = new RegExp(FindText, "g");
    return str.replace(regExp, RepText);
}
