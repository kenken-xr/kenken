// canvas生成图片
function canvasToImage() {
    // svg转canvas
    let nodesToRecover = [];
    let nodesToRemove = [];
    let svgElem = $("#mcontainer").find("svg"); // divReport为需要截取成图片的dom的id
    let prepare = [];
    for (let i = 0; i < svgElem.length; i++) {
        prepare.push(0);
    }
    svgElem.each(function (index, node) {
        let parentNode = node.parentNode;
        let svg = node.outerHTML;
        let canvas = document.createElement("canvas");
        canvg(canvas, svg);
        canvas.style.position = node.style.position;
        canvas.style.width = node.style.width;
        canvas.style.height = node.style.height;
        canvas.style.left = 0;
        canvas.style.top = 0;
        let ctx = canvas.getContext("2d");
        let svg_xml = new XMLSerializer().serializeToString(node);
        let img = new Image();
        img.src = "data:image/svg+xml;base64," + window.btoa(svg_xml);
        img.onload = function () {
            ctx.drawImage(img, 0, 0);
            prepare[index] = 1;
            // download(canvas.toDataURL("image/png")); // 调试用
        };
        parentNode.appendChild(canvas); // 使用canvas代替svg进行截图
        nodesToRemove.push({
            // 完成截图后删除canvas
            parent: parentNode,
            child: canvas,
        });
        nodesToRecover.push({
            // 完成截图后恢复svg
            parent: parentNode,
            child: node,
        });
        parentNode.removeChild(node); // 暂时移除svg
    });
    let waitInterval = setInterval(() => {
        let isComplete = true;
        for (let i = 0; i < prepare.length; i++) {
            if (prepare[i] === 0) {
                isComplete = false;
                break;
            }
        }
        if (isComplete) {
            clearInterval(waitInterval);
            // div转canvas截图
            html2canvas(document.getElementById("container"), {
                useCORS: true,
            }).then((cnv) => {
                let imgUrl = cnv.toDataURL("image/png"); // 将canvas转换成img的src流，base64
                for (let i = 0; i < nodesToRecover.length; i++) {
                    nodesToRecover[i].parent.appendChild(nodesToRecover[i].child);
                }
                for (let i = 0; i < nodesToRemove.length; i++) {
                    nodesToRemove[i].parent.removeChild(nodesToRemove[i].child);
                }
                download(imgUrl);
            });
        }
    }, 5);

    html2canvas(document.getElementById("container"), {
        useCORS: true,
    }).then((cnv) => {
        //console.log(cnv);
        let imgUrl = cnv.toDataURL("image/png"); // 将canvas转换成img的src流，base64
        for (let i = 0; i < nodesToRecover.length; i++) {
            nodesToRecover[i].parent.appendChild(nodesToRecover[i].child);
        }
        for (let i = 0; i < nodesToRemove.length; i++) {
            nodesToRemove[i].parent.removeChild(nodesToRemove[i].child);
        }
        download(imgUrl);
    });
}

/* 下载图片的方法 */
function download(url) {
    let a = document.createElement("a");
    a.style.display = "none";
    document.body.appendChild(a);
    let triggerDownload = $(a).attr("href", url).attr("download", "test");
    triggerDownload[0].click();
    document.body.removeChild(a);
}