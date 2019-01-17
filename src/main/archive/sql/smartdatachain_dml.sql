/*
 * 数据库表插入语句
 * @author haolen
 * @version 1.0
 * @time 2018/10/23
 */

/*
 * 下面是给本地任务组添加分组
 */
INSERT INTO `work_group_local` ( `user_id`, `name`) VALUES ( 1, '默认分组');
INSERT INTO `work_group_local` ( `user_id`, `name`) VALUES ( 2, '本地采集');

/*
 * 下面是个本地任务组添加任务
 */
INSERT INTO `work_group_local_detail` ( `info`, `name`, `type`, `status`, `time_create`, `graph`, `type_sub`, `count_local`, `collection_id`, `rule`) VALUES
 ( '', 'thinkjs - CSDN搜索-采集', 'crawler', 'init', '2018-10-23 16:05:45', '{"attr":{"listSelector":"//div[contains(@class,''main-container'')]/div[contains(@class,''con-l'')]/div[contains(@class,''search-list-con'')]//dl[contains(@class,''search-list'')]","extractFields":{"标题":{"extractType":"text","notNull":false},"标题链接":{"extractType":"href","notNull":false},"search-detail":{"extractType":"text","notNull":false},"作者：":{"extractType":"text","notNull":false},"字段0":{"extractType":"text","notNull":false}},"settings":{"loadImages":true,"loadAds":true,"userAgent":false,"reborn":{"cookie":0,"userAgent":0},"basePath":"C:\\Users\\Administrator\\AppData\\Local\\Houyi\\images\\2447737","proxy":false,"timeoutAjax":1000,"downloadImage":false,"cron":{"frequency":"once","once_date_start_which":"1","once_date_start":"2018-10-23","date_between_which":"1","date_between":"2018-10-23 - 2018-10-23","time_start_which":"1","time_start":"16:8","time_end_which":"1","time_end":"00:00","realtime_interval":"600"},"startCron":false,"publishAutomatic":false,"savedConfigName":""},"session_id":"1540278376231","urls":["https://so.csdn.net/so/search/s.do?q=thinkjs&t=&o=&s=&l="]},"script":[],"pageType":"list","pager":{"action":"","type":"no-next","clickedValue":"","selector":"/html/body/div[@class=''main-container'']/div[@class=''con-l'']/div[@class=''csdn-pagination hide-set'']/span[@class=''page-nav'']/a[@class=''btn btn-xs btn-default btn-next'']","pageNum":"-1"},"fields":[{"name":"标题","selector":"//dd[contains(@class,''search-link'')]/a","extractType":"text","notNull":false,"inList":true},{"name":"标题链接","selector":"//dd[contains(@class,''search-link'')]/a","extractType":"href","notNull":false,"inList":true},{"name":"search-detail","selector":"//dd[contains(@class,''search-detail'')]/em[1]","extractType":"text","notNull":false,"inList":true},{"name":"作者：","selector":"//dd[contains(@class,''author-time'')]/a[1]","extractType":"text","notNull":false,"inList":true},{"name":"字段0","selector":"//dt[1]/a[1]","extractType":"text","notNull":false,"inList":true}]}', 'auto', '494', 10705, '');


