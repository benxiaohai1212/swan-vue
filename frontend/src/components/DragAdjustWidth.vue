<template>
	<div class="resize" title="收缩侧边栏">⋮</div>
</template>
<script>
export default {
	name: "dragAdjustWidth",
	props: ["styleLoc"],
	data() {
		return {
			screenWidth: document.body.clientWidth
		};
	},
	created() { },
	mounted() {
		this.dragControllerDiv();
		let _this = this; //赋值vue的this
		window.addEventListener("resize", () => {
			this.screenWidth = document.body.clientWidth;
		});
	},
	watch: {
		screenWidth(newVal, oldVal) {
			let styleLoc = this.styleLoc;
			var resize = document.getElementsByClassName("resize");
			var mid = document.getElementsByClassName(styleLoc.right);
			var box = document.getElementsByClassName(styleLoc.box);
			var maxT = box.clientWidth - resize.offsetWidth;
			if (moveLen > maxT - 600) moveLen = maxT - 600; //右边区域最小宽度为600px
			var moveLen = resize.left;
			for (let i = 0; i < resize.length; i++) {
				mid[i].style.width = newVal - moveLen + "px";
			}
		}
	},
	components: {},
	methods: {
		dragControllerDiv() {
			let styleLoc = this.styleLoc;
			var resize = document.getElementsByClassName("resize");
			var left = document.getElementsByClassName(styleLoc.left);
			var mid = document.getElementsByClassName(styleLoc.right);
			var box = document.getElementsByClassName(styleLoc.box);
			let that = this;
			for (let i = 0; i < resize.length; i++) {
				// 鼠标按下事件
				resize[i].onmousedown = function (e) {
					//颜色改变提醒
					resize[i].style.background = "#818181";
					var startX = e.clientX;
					resize[i].left = resize[i].offsetLeft;
					that.compatibleStyle(mid[0], "none");
					// 鼠标拖动事件
					document.onmousemove = function (e) {
						var endX = e.clientX;
						var moveLen = resize[i].left + (endX - startX); // （endx-startx）=移动的距离。resize[i].left+移动的距离=左边区域最后的宽度
						var maxT = box[i].clientWidth - resize[i].offsetWidth; // 容器宽度 - 左边区域的宽度 = 右边区域的宽度
						if (moveLen < 200) moveLen = 200; // 左边区域的最小宽度为32px
						if (moveLen > maxT - 600) moveLen = maxT - 600; //右边区域最小宽度为600px
						resize[i].style.left = (moveLen - 5) + 'px'; // 设置左侧区域的宽度

						for (let j = 0; j < left.length; j++) {
							left[j].style.width = moveLen + "px";
							mid[j].style.width = box[i].clientWidth - moveLen - 10 + "px";
						}
					};
					// 鼠标松开事件
					document.onmouseup = function (evt) {
						that.compatibleStyle(mid[0], "auto");
						//颜色恢复
						resize[i].style.background = "#F3F9FF";
						document.onmousemove = null;
						document.onmouseup = null;
						resize[i].releaseCapture && resize[i].releaseCapture(); //当你不在需要继续获得鼠标消息就要应该调用ReleaseCapture()释放掉
					};
					resize[i].setCapture && resize[i].setCapture(); //该函数在属于当前线程的指定窗口里设置鼠标捕获
					return false;
				};
			}
		},
		compatibleStyle(node, info) {
			if (node.getElementsByTagName("iframe").length) {
				node.getElementsByTagName(
					"iframe"
				)[0].style.pointerEvents = info;
			}
			if (node.getElementsByTagName("embed").length) {
				node.getElementsByTagName(
					"embed"
				)[0].style.pointerEvents = info;
			}
		}
	},
	beforeDestroy() {
		window.onresize = null;
	}
};
</script>
<style lang="scss" scoped>
/*拖拽区div样式*/
.resize {
	cursor: col-resize;
	// float: left;
	position: relative;
	top: 300px;
	background-color: #d6d6d6;
	border-radius: 5px;
	margin-top: -10px;
	width: 10px;
	height: 50px;
	line-height: 50px;
	background-size: cover;
	background-position: center;
	/*z-index: 99999;*/
	font-size: 32px;
	color: white;
}

/*拖拽区鼠标悬停样式*/
.resize:hover {
	color: #444444;
}
</style>