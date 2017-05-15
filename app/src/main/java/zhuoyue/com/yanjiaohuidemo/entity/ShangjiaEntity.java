package zhuoyue.com.yanjiaohuidemo.entity;

import java.util.List;

/**
 * Created by ShellJor on 2017/5/13 0013.
 * at 15:54
 */

public class ShangjiaEntity {

    private List<AdvBannerBean> adv_banner;
    private List<AdvStrBean> adv_str;
    private List<IndexModelBean> index_model;
    private List<NewsBean> news;
    private List<MidAdvBean> mid_adv;
    private List<YouhuiLeftBean> youhui_left;
    private List<YouhuiRightBean> youhui_right;
    private List<YouhuiDownBean> youhui_down;
    //商家列表
    private List<SupplierListBean> Supplier_list;

    public List<AdvBannerBean> getAdv_banner() {
        return adv_banner;
    }

    public void setAdv_banner(List<AdvBannerBean> adv_banner) {
        this.adv_banner = adv_banner;
    }

    public List<AdvStrBean> getAdv_str() {
        return adv_str;
    }

    public void setAdv_str(List<AdvStrBean> adv_str) {
        this.adv_str = adv_str;
    }

    public List<IndexModelBean> getIndex_model() {
        return index_model;
    }

    public void setIndex_model(List<IndexModelBean> index_model) {
        this.index_model = index_model;
    }

    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }

    public List<MidAdvBean> getMid_adv() {
        return mid_adv;
    }

    public void setMid_adv(List<MidAdvBean> mid_adv) {
        this.mid_adv = mid_adv;
    }

    public List<YouhuiLeftBean> getYouhui_left() {
        return youhui_left;
    }

    public void setYouhui_left(List<YouhuiLeftBean> youhui_left) {
        this.youhui_left = youhui_left;
    }

    public List<YouhuiRightBean> getYouhui_right() {
        return youhui_right;
    }

    public void setYouhui_right(List<YouhuiRightBean> youhui_right) {
        this.youhui_right = youhui_right;
    }

    public List<YouhuiDownBean> getYouhui_down() {
        return youhui_down;
    }

    public void setYouhui_down(List<YouhuiDownBean> youhui_down) {
        this.youhui_down = youhui_down;
    }

    public List<SupplierListBean> getSupplier_list() {
        return Supplier_list;
    }

    public void setSupplier_list(List<SupplierListBean> Supplier_list) {
        this.Supplier_list = Supplier_list;
    }

    public static class AdvBannerBean {
        /**
         * id : 4
         * type : 0
         * typeid : 1
         * adv_name : 首页焦点图4
         * adv_url : null
         * adv_model : index
         * adv_id : null
         * is_delete : 0
         * litpic : /Public/Uploads/20170510/banner_1.png
         * city_id : 0
         */

        private String id;
        private String type;
        private String typeid;
        private String adv_name;
        private Object adv_url;
        private String adv_model;
        private Object adv_id;
        private String is_delete;
        private String litpic;
        private String city_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getAdv_name() {
            return adv_name;
        }

        public void setAdv_name(String adv_name) {
            this.adv_name = adv_name;
        }

        public Object getAdv_url() {
            return adv_url;
        }

        public void setAdv_url(Object adv_url) {
            this.adv_url = adv_url;
        }

        public String getAdv_model() {
            return adv_model;
        }

        public void setAdv_model(String adv_model) {
            this.adv_model = adv_model;
        }

        public Object getAdv_id() {
            return adv_id;
        }

        public void setAdv_id(Object adv_id) {
            this.adv_id = adv_id;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }

        public String getLitpic() {
            return litpic;
        }

        public void setLitpic(String litpic) {
            this.litpic = litpic;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }
    }

    public static class AdvStrBean {
        /**
         * id : 8
         * type : 1
         * typeid : 2
         * adv_name : 文字广告跳转4
         * adv_url : null
         * adv_model : index
         * adv_id : null
         * is_delete : 0
         * litpic : null
         * city_id : 0
         */

        private String id;
        private String type;
        private String typeid;
        private String adv_name;
        private Object adv_url;
        private String adv_model;
        private Object adv_id;
        private String is_delete;
        private Object litpic;
        private String city_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getAdv_name() {
            return adv_name;
        }

        public void setAdv_name(String adv_name) {
            this.adv_name = adv_name;
        }

        public Object getAdv_url() {
            return adv_url;
        }

        public void setAdv_url(Object adv_url) {
            this.adv_url = adv_url;
        }

        public String getAdv_model() {
            return adv_model;
        }

        public void setAdv_model(String adv_model) {
            this.adv_model = adv_model;
        }

        public Object getAdv_id() {
            return adv_id;
        }

        public void setAdv_id(Object adv_id) {
            this.adv_id = adv_id;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }

        public Object getLitpic() {
            return litpic;
        }

        public void setLitpic(Object litpic) {
            this.litpic = litpic;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }
    }

    public static class IndexModelBean {
        /**
         * id : 8
         * topid : 0
         * name : 测试分类
         * model : null
         * mid : 0
         * url :
         * icon : ./Public/logo/icon-quanbufenlei.png
         * is_delete : 0
         * city_id : 0
         */

        private String id;
        private String topid;
        private String name;
        private Object model;
        private String mid;
        private String url;
        private String icon;
        private String is_delete;
        private String city_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTopid() {
            return topid;
        }

        public void setTopid(String topid) {
            this.topid = topid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getModel() {
            return model;
        }

        public void setModel(Object model) {
            this.model = model;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }
    }

    public static class NewsBean {
        /**
         * id : 1
         * uid : 593
         * typeid : 1
         * typename : 燕郊新闻
         * title : 测试下新闻
         * description : 测试下新闻
         * body : 测试下新闻
         * time :
         * writer :
         * goodpost : null
         * badpost : null
         * praise_count : null
         * litpic :
         * imgsurl :
         * is_delete : 0
         * city_id : 0
         * weight : 1
         * flag :
         * is_review : 0
         */

        private String id;
        private String uid;
        private String typeid;
        private String typename;
        private String title;
        private String description;
        private String body;
        private String time;
        private String writer;
        private Object goodpost;
        private Object badpost;
        private Object praise_count;
        private String litpic;
        private String imgsurl;
        private String is_delete;
        private String city_id;
        private String weight;
        private String flag;
        private String is_review;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getWriter() {
            return writer;
        }

        public void setWriter(String writer) {
            this.writer = writer;
        }

        public Object getGoodpost() {
            return goodpost;
        }

        public void setGoodpost(Object goodpost) {
            this.goodpost = goodpost;
        }

        public Object getBadpost() {
            return badpost;
        }

        public void setBadpost(Object badpost) {
            this.badpost = badpost;
        }

        public Object getPraise_count() {
            return praise_count;
        }

        public void setPraise_count(Object praise_count) {
            this.praise_count = praise_count;
        }

        public String getLitpic() {
            return litpic;
        }

        public void setLitpic(String litpic) {
            this.litpic = litpic;
        }

        public String getImgsurl() {
            return imgsurl;
        }

        public void setImgsurl(String imgsurl) {
            this.imgsurl = imgsurl;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getIs_review() {
            return is_review;
        }

        public void setIs_review(String is_review) {
            this.is_review = is_review;
        }
    }

    public static class MidAdvBean {
        /**
         * id : 9
         * type : 0
         * typeid : 3
         * adv_name : 首页中部广告位
         * adv_url : null
         * adv_model : index
         * adv_id : null
         * is_delete : 0
         * litpic : /Public/Uploads/20170510/banner_1.png
         * city_id : 0
         */

        private String id;
        private String type;
        private String typeid;
        private String adv_name;
        private Object adv_url;
        private String adv_model;
        private Object adv_id;
        private String is_delete;
        private String litpic;
        private String city_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getAdv_name() {
            return adv_name;
        }

        public void setAdv_name(String adv_name) {
            this.adv_name = adv_name;
        }

        public Object getAdv_url() {
            return adv_url;
        }

        public void setAdv_url(Object adv_url) {
            this.adv_url = adv_url;
        }

        public String getAdv_model() {
            return adv_model;
        }

        public void setAdv_model(String adv_model) {
            this.adv_model = adv_model;
        }

        public Object getAdv_id() {
            return adv_id;
        }

        public void setAdv_id(Object adv_id) {
            this.adv_id = adv_id;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }

        public String getLitpic() {
            return litpic;
        }

        public void setLitpic(String litpic) {
            this.litpic = litpic;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }
    }

    public static class YouhuiLeftBean {
        /**
         * id : 10
         * type : 0
         * typeid : 4
         * adv_name : 首页优惠专区左侧
         * adv_url : null
         * adv_model : index
         * adv_id : null
         * is_delete : 0
         * litpic : /Public/images/yh_left.png
         * city_id : 0
         */

        private String id;
        private String type;
        private String typeid;
        private String adv_name;
        private Object adv_url;
        private String adv_model;
        private Object adv_id;
        private String is_delete;
        private String litpic;
        private String city_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getAdv_name() {
            return adv_name;
        }

        public void setAdv_name(String adv_name) {
            this.adv_name = adv_name;
        }

        public Object getAdv_url() {
            return adv_url;
        }

        public void setAdv_url(Object adv_url) {
            this.adv_url = adv_url;
        }

        public String getAdv_model() {
            return adv_model;
        }

        public void setAdv_model(String adv_model) {
            this.adv_model = adv_model;
        }

        public Object getAdv_id() {
            return adv_id;
        }

        public void setAdv_id(Object adv_id) {
            this.adv_id = adv_id;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }

        public String getLitpic() {
            return litpic;
        }

        public void setLitpic(String litpic) {
            this.litpic = litpic;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }
    }

    public static class YouhuiRightBean {
        /**
         * id : 14
         * type : 0
         * typeid : 5
         * adv_name : 首页优惠专区右侧4
         * adv_url : null
         * adv_model : index
         * adv_id : null
         * is_delete : 0
         * litpic : /Public/images/yh_right4.png
         * city_id : 0
         */

        private String id;
        private String type;
        private String typeid;
        private String adv_name;
        private Object adv_url;
        private String adv_model;
        private Object adv_id;
        private String is_delete;
        private String litpic;
        private String city_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getAdv_name() {
            return adv_name;
        }

        public void setAdv_name(String adv_name) {
            this.adv_name = adv_name;
        }

        public Object getAdv_url() {
            return adv_url;
        }

        public void setAdv_url(Object adv_url) {
            this.adv_url = adv_url;
        }

        public String getAdv_model() {
            return adv_model;
        }

        public void setAdv_model(String adv_model) {
            this.adv_model = adv_model;
        }

        public Object getAdv_id() {
            return adv_id;
        }

        public void setAdv_id(Object adv_id) {
            this.adv_id = adv_id;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }

        public String getLitpic() {
            return litpic;
        }

        public void setLitpic(String litpic) {
            this.litpic = litpic;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }
    }

    public static class YouhuiDownBean {
        /**
         * id : 14
         * type : 0
         * typeid : 5
         * adv_name : 首页优惠专区右侧4
         * adv_url : null
         * adv_model : index
         * adv_id : null
         * is_delete : 0
         * litpic : /Public/images/yh_right4.png
         * city_id : 0
         */

        private String id;
        private String type;
        private String typeid;
        private String adv_name;
        private Object adv_url;
        private String adv_model;
        private Object adv_id;
        private String is_delete;
        private String litpic;
        private String city_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getAdv_name() {
            return adv_name;
        }

        public void setAdv_name(String adv_name) {
            this.adv_name = adv_name;
        }

        public Object getAdv_url() {
            return adv_url;
        }

        public void setAdv_url(Object adv_url) {
            this.adv_url = adv_url;
        }

        public String getAdv_model() {
            return adv_model;
        }

        public void setAdv_model(String adv_model) {
            this.adv_model = adv_model;
        }

        public Object getAdv_id() {
            return adv_id;
        }

        public void setAdv_id(Object adv_id) {
            this.adv_id = adv_id;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }

        public String getLitpic() {
            return litpic;
        }

        public void setLitpic(String litpic) {
            this.litpic = litpic;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }
    }

    public static class SupplierListBean {
        /**
         * id : 97
         * name : 易星科技
         * typename : 美食
         * stype : 0
         * route : 302天洋城4代直接下
         * address : 北京东燕郊
         * ref_avg_price : 20.0000
         * good_rate : 3.0000
         * city_id : 0
         * good_dp_count : 1232132131
         * index_img : ./Public/Uploads/20170508/590fdb489466c.jpg
         * juli : 2810.83215683715
         * zonghe : 12321321.4276471
         */

        private String id;
        private String name;
        private String typename;
        private String stype;
        private String route;
        private String address;
        private String ref_avg_price;
        private String good_rate;
        private String city_id;
        private String good_dp_count;
        private String index_img;
        private String juli;
        private String zonghe;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public String getStype() {
            return stype;
        }

        public void setStype(String stype) {
            this.stype = stype;
        }

        public String getRoute() {
            return route;
        }

        public void setRoute(String route) {
            this.route = route;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRef_avg_price() {
            return ref_avg_price;
        }

        public void setRef_avg_price(String ref_avg_price) {
            this.ref_avg_price = ref_avg_price;
        }

        public String getGood_rate() {
            return good_rate;
        }

        public void setGood_rate(String good_rate) {
            this.good_rate = good_rate;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getGood_dp_count() {
            return good_dp_count;
        }

        public void setGood_dp_count(String good_dp_count) {
            this.good_dp_count = good_dp_count;
        }

        public String getIndex_img() {
            return index_img;
        }

        public void setIndex_img(String index_img) {
            this.index_img = index_img;
        }

        public String getJuli() {
            return juli;
        }

        public void setJuli(String juli) {
            this.juli = juli;
        }

        public String getZonghe() {
            return zonghe;
        }

        public void setZonghe(String zonghe) {
            this.zonghe = zonghe;
        }
    }
}
