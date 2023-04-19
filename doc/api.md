1.  影片分页接口

/view/movie/page?page=1&pageSize=20&starId=xx&tagId=xx

{
  // 影片列表
  "rows": [
    {
      "date": "2022-07-21",
      "id": "DLDSS-097",
      "img": "https://www.javbus.com/pics/thumb/915m.jpg",
      "title": "谷間を魅せつけ視線で誘惑。僕の思春期、毎日セックスをしてくれた姉が今…"
    }
  ],
  // 分页信息
  "pagination": {
    "currentPage": 1,
    "hasNextPage": true,
    "nextPage": 2,
    "pages": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
  },
  // 演员信息，注意：只有在请求参数包含 starId 时才会返回
  "star": {
    "avatar": "https://www.javbus.com/pics/actress/2xi_a.jpg",
    "id": "2xi",
    "name": "葵つかさ",
    "birthday": "1990-08-14",
    "age": "31",
    "height": "163cm",
    "bust": "88cm",
    "waistline": "58cm",
    "hipline": "86cm",
    "birthplace": "大阪府",
    "hobby": "ジョギング、ジャズ鑑賞、アルトサックス、ピアノ、一輪車"
  }
}
2.  影片关键字查询
/view/movie/search?page=1&pageSize=20&keyword=xxxx
{
  // 影片列表
  "rows": [
    {
      "date": "2020-08-15",
      "id": "SSNI-845",
      "img": "https://www.javbus.com/pics/thumb/7t44.jpg",
      "title": "彼女の姉は美人で巨乳しかもドS！大胆M性感プレイでなす術もなくヌキまくられるドMな僕。 三上悠亜"
    }
    // ...
  ],
  // 分页信息
  "pagination": {
    "currentPage": 2,
    "hasNextPage": true,
    "nextPage": 3,
    "pages": [1, 2, 3, 4, 5]
  },
  "keyword": "三上"
}
3. /view/movie/detail?id=
{
  "id": "SSIS-406",
  "title": "SSIS-406 才色兼備な女上司が思う存分に羽目を外し僕を連れ回す【週末限定】裏顔デート 葵つかさ",
  "img": "https://www.javbus.com/pics/cover/8xnc_b.jpg",
  // 封面大图尺寸
  "imageSize": {
    "width": 800,
    "height": 538
  },
  "date": "2022-05-20",
  // 影片时长
  "videoLength": 120,
  "director": {
    "directorId": "hh",
    "directorName": "五右衛門"
  },
  "producer": {
    "producerId": "7q",
    "producerName": "エスワン ナンバーワンスタイル"
  },
  "publisher": {
    "publisherId": "9x",
    "publisherName": "S1 NO.1 STYLE"
  },
  "series": {
    "seriesId": "xx",
    "seriesName": "xx"
  },
  "tags": [
    {
      "tagId": "e",
      "tagName": "巨乳"
    }
    // ...
  ],
  // 演员信息，一部影片可能包含多个演员
  "stars": [
    {
      "starId": "2xi",
      "starName": "葵つかさ"
    }
  ],
  // 影片预览图
  "samples": [
    {
      "alt": "SSIS-406 才色兼備な女上司が思う存分に羽目を外し僕を連れ回す【週末限定】裏顔デート 葵つかさ - 樣品圖像 - 1",
      "id": "8xnc_1",
      // 大图
      "src": "https://pics.dmm.co.jp/digital/video/ssis00406/ssis00406jp-1.jpg",
      // 缩略图
      "thumbnail": "https://www.javbus.com/pics/sample/8xnc_1.jpg"
    }
    // ...
  ]
}
4. 影星详情
/view/star/detail?id
{
  "avatar": "https://www.javbus.com/pics/actress/2xi_a.jpg",
  "id": "2xi",
  "name": "葵つかさ",
  "birthday": "1990-08-14",
  "age": "32",
  "height": "163cm",
  "bust": "88cm",
  "waistline": "58cm",
  "hipline": "86cm",
  "birthplace": "大阪府",
  "hobby": "ジョギング、ジャズ鑑賞、アルトサックス、ピアノ、一輪車"
}
5. 获取磁力链接
/view/magnet/list?movieId=
{
  // 磁力链接列表
  "magnets": [
    {
      "link": "magnet:?xt=urn:btih:A6D7C90FAB7E4223C61425A2E4CDF9E503CEDAA2&dn=SSIS-406-C",
      // 是否高清
      "isHD": true,
      "title": "SSIS-406-C",
      "size": "5.46GB",
      // bytes
      "numberSize": 5862630359,
      "shareDate": "2022-05-20",
      // 是否包含字幕
      "hasSubtitle": true
    }
    // ...
  ]
}

6. 影星列表

7. tag列表

