!function(e,t){function n(){var n=l.getBoundingClientRect().width;t=t||540,n>t&&(n=t);var i=100*n/e;r.innerHTML="html{font-size:"+i+"px;}"}var i,d=document,o=window,l=d.documentElement,r=document.createElement("style");if(l.firstElementChild)l.firstElementChild.appendChild(r);else{var a=d.createElement("div");a.appendChild(r),d.write(a.innerHTML),a=null}n(),o.addEventListener("resize",function(){clearTimeout(i),i=setTimeout(n,300),document.getElementById("content").style.height = window.innerHeight+'px';document.getElementById("popup").style.height = window.innerHeight+'px';},!1),o.addEventListener("pageshow",function(e){e.persisted&&(clearTimeout(i),i=setTimeout(n,300))},!1),"complete"===d.readyState?d.body.style.fontSize="16px":d.addEventListener("DOMContentLoaded",function(e){d.body.style.fontSize="16px",document.getElementById("content").style.height = window.innerHeight+'px';document.getElementById("popup").style.height = window.innerHeight+'px';},!1)}(750,750);

var data = [
    {
        question: "请选择正确答案:",
        img: "./img/do.png",
        answer: ["Do", "Mi", "Sol"],
        suc: "a"
    },
    {
        question: "请选择正确答案:",
        img: "./img/re.png",
        answer: ["Re", "Fa", "La"],
        suc: "a"
    },
    {
        question: "请选择正确答案:",
        img: "./img/mi.png",
        answer: ["La", "Mi", "Re"],
        suc: "b"
    },
    {
        question: "请选择正确答案:",
        img: "./img/sol.png",
        answer: ["Do", "La", "Sol"],
        suc: "c"
    },
    {
        question: "请选择正确答案:",
        img: "./img/fa.png",
        answer: ["Fa", "Sol", "Do"],
        suc: "a"
    },
    {
        question: "请选择正确答案:",
        img: "./img/la.png",
        answer: ["Fa", "Mi", "La"],
        suc: "c"
    },
    {
        question: "这些是几分音符你认识吗？",
        img: "./img/4fa.png",
        answer: ["Mi", "Fa", "四分音符", "二分音符"],
        suc: "c"
    },
    {
        question: "这些是几分音符你认识吗？",
        img: "./img/2fa.png",
        answer: ["Fa", "四分音符", "二分音符", "Mi"],
        suc: "c"
    },
    {
        question: "下面的第几个乐句划分是错误的？",
        img: "./img/twinklestar.png",
        answer: ["第一行", "第二行", "第三行"],
        suc: "c"
    }
];


(function(){
    $(document).on('click', '#list a', function() {
        // $(this).toggleClass('on');
        $(this).addClass('on').andSelf().siblings().removeClass('on');
    });
    $(document).on('click', '#btnNext', function() {
        var next = Number($(this).attr('now')) + 1;
        switchQuestion(next);
        window.android.next(next);
    })
    $(document).on('click', '#questionCont a', function () {
        var sq = Number($(this).attr('num'));
        $('#questionList').remove();
        switchQuestion(sq);
        window.android.next(sq);
    })
})()

var nown = 0;
var switchQuestion = function(n) {
    if(n >= 1) {
        n = n - 1;
    }else {
        n = 0;
    }
    nown = n;
    var q = '';
    q += '<h3>' + (n + 1) + '、' + data[n].question + '</h3>';
    q += '<img src="' + data[n].img + '" alt="">';
    q += '<div class="answer">';
    q += '<div id="list" answer="' + data[n].suc + '">';
    data[n].answer.forEach(function(a, b) {
        q += '<a href="javascript:void(0)">' + a + '</a>';
    })
    q += '</div>';
    q += '</div>';
    if(n < data.length-1){
        q += '<button class="next" id="btnNext" now="' + (n + 1) + '">下一题</button>';
    }

    $('#content').html(q);
    $('.popup-body').html('');
}

switchQuestion();

function callJS(d) {
    var num = d.indexOf('=');
    var name = d.substr(0, num);
    var ans = d.substr(num+1);
    var suc = $('#list').attr('answer');
    var cla = suc.toUpperCase() == ans.toUpperCase() ? "suc" : "err";
    var stu = '';
    stu += '<a href="javascript:void(0)" class="stu-item">';
    stu += '<span class="' + cla + '">' + ans.toUpperCase() + '</span>';
    stu += '<p>' + name + '</p>';
    stu += '</a>';

    $('.popup-body').append(stu);
}

function getQuestionList() {

    if($('#questionList').length > 0) {
        $('#questionList').remove();
    }else{
        var dl = data.length;
        var ql = '';
        ql += '<div class="question-list" id="questionList">';
        ql += '<div class="question-cont" id="questionCont">';
        for(var a = 1; a <= dl; a++) {
            ql += '<a href="javascript:void(0)" num="' + a + '">' + a + '</a>';
        }
        ql += '</div>';
        ql += '</div>';
    
        $('body').append(ql);
        $('#questionCont a').eq(nown).focus();
    }
}