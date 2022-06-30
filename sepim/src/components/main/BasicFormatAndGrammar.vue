<template>
    <div class="sp-content">
        <h1>基本撰写和格式语法</h1>
        <div class="sp-p">
            <p>使用简单的语法为您的散文和代码创建复杂的格式。</p>
        </div>
        <div class="markdown-body" v-html="content">
        </div>
    </div>
</template>

<script>
import 'mavon-editor/dist/markdown/github-markdown.min.css';
import 'mavon-editor/dist/highlightjs/styles/github.min.css';
export default {
    name: "BasicFormatAndGrammar",
    data(){
        return {
            content:'\n' +
                '<h2><a id="_0"></a>标题</h2> <p>要创建标题，请在标题文本前添加一至六个 # 符号。 您使用的 # 数量将决定标题的大小。</p>' +
                ' <pre><code class="lang-"># 最大标题 ## 第二大标题 ###### 最小标题 </code></pre> <p>' +
                '<img src="http://localhost:8088/upload/sepim/md/img/17a1a538a3d64deab3eddb6f0108203dimage.png" alt="image.png" />' +
                '</p> <h2><a id="_9"></a>样式文本</h2> <p>您可以在.md 文件中以粗体、斜体、删除线、下标或上标的文字表示强调。</p> ' +
                '<table><thead><tr><th>样式</th><th>语法</th><th>键盘快捷键</th><th>示例</th><th>输出</th></tr></thead><tbody>' +
                '<tr><td>粗体</td><td><code>** **</code> 或 <code>__ __</code></td><td>&lt;kbd&gt;Command&lt;/kbd&gt;+&lt;kbd&gt;B&lt;/kbd&gt; (Mac) 或 &lt;kbd&gt;Ctrl&lt;/kbd&gt;+&lt;kbd&gt;B&lt;/kbd&gt;' +
                ' (Windows/Linux)</td><td><code>**这是粗体文本**</code></td><td><strong>这是粗体文本</strong></td></tr><tr><td>斜体</td><td><code>* *</code> 或 <code>_ _</code>' +
                '     </td><td>&lt;kbd&gt;Command&lt;/kbd&gt;+&lt;kbd&gt;I&lt;/kbd&gt; (Mac) 或 &lt;kbd&gt;Ctrl&lt;/kbd&gt;+&lt;kbd&gt;I&lt;/kbd&gt; (Windows/Linux)</td><td><code>*这是斜体文本*</code></td><td><em>这是斜体文本' +
                '</em></td></tr><tr><td>删除线</td><td><code>~~ ~~</code></td><td></td><td><code>~~这是错误文本~~</code></td><td><del>这是错误文本</del></td></tr><tr><td>粗体和嵌入的斜体</td><td><code>** **</code> ' +
                '和 <code>_ _</code></td><td></td><td><code>**此文本 _非常_ 重要**</code></td><td><strong>此文本<em>非常</em>重要</strong></td></tr><tr><td>全部粗体和斜体</td><td><code>*** ***</code></td><td></td><td><code>***所有这些文本都很重要***' +
                '</code></td><td><strong><em>所有这些文本都是斜体</em></strong></td></tr><tr><td>下标</td><td><code>&lt;sub&gt; &lt;/sub&gt;</code></td><td></td><td><code>&lt;sub&gt;这是下标文本&lt;/&lt;wbr&gt;sub&gt;</code></td><td><sub>这是下标文本' +
                '</sub></td></tr><tr><td>上标</td><td><code>&lt;sup&gt; &lt;/sup&gt;</code></td><td></td><td><code>&lt;sup&gt;这是上标文本&lt;/&lt;wbr&gt;sup&gt;</code></td><td><sup>这是上标文本</sup></td></tr></tbody></table> <h2><a id="_14"></a>引用文本</h2> ' +
                '<p>您可以使用 &gt; 引用文本。</p> <pre><code>Text that is not a quote <span>&gt; Text that is a quote</span> </code></pre> <h2><a id="_22"></a>列表</h2> <p>通过在一行或多行文本前面添加 &lt;kbd&gt;-&lt;/kbd&gt; 或 &lt;kbd&gt;*&lt;/kbd&gt; ' +
                '可创建无序列表。</p> <pre><code><span>-</span> George Washington <span>-</span> John Adams <span>-</span> Thomas Jefferson </code></pre> 要对列表排序，请在每行前面添加一个编号。 <pre><code><span>1.</span> James Madison <span>2.</span> ' +
                'James Monroe <span>3.</span> John Quincy Adams </code></pre> <h2><a id="_34"></a>嵌套列表</h2> <p>通过在一个列表项下面缩进一个或多个其他列表项，可创建嵌套列表。</p>' +
                ' <p>要通过 GitHub 上的 web 编辑器或使用等宽字体的文本编辑器（例如 Atom）创建嵌套列表，您可以直观地对齐列表。 在嵌套列表项的前面键入空格字符，直至列表标记字符（- 或 *）位于其上方条目中第一个文本字符的正下方。</p> <pre><code><span>1.</span> ' +
                '第一个列表项 <span> -</span> 第一个嵌套列表项 <span> -</span> 第二个嵌套列表项 </code></pre> <h2><a id="_43"></a>使用表情符号</h2> <p>通过键入 :EMOJICODE: 可在您的写作中添加表情符号。</p> <p>@octocat 👍 这个 PR 看起来很棒 - 可以合并了！ ' +
                ':shipit:<br /> 键入 : 将显示建议的表情符号列表。 列表将在您键入时进行过滤，因此一旦找到所需表情符号，请按 Tab 或 Enter 键以填写选中的结果。</p> <p>有关可用表情符号和代码的完整列表，请查看<a href="https://github.com/ikatyang/emoji-cheat-sheet/blob/master/README.md">' +
                '表情符号备忘清单</a>。</p> <h2><a id="_50"></a>段落</h2> <p>通过在文本行之间留一个空白行，可创建新段落。</p> <h2><a id="_53"></a>脚注</h2> <p>您可以使用此括号语法为您的内容添加脚注：</p> <pre><code>Here is a simple footnote[^1]. A footnote can also have multip' +
                'le lines[^2]. You can also use words, to fit your writing style more closely[^note]. [^1]: My reference. [^2]: Every new line should be prefixed with 2 spaces. This allows you to have a footnote with multiple lines. [^note]: Named footnotes will still re' +
                'nder with numbers instead of the text but allow easier identification and linking. This footnote also has been made with a different syntax using 4 spaces for new lines. </code></pre> <h2><a id="_Markdown__68"></a>忽略 Markdown 格式</h2> <p>通过在 Markdown' +
                ' 字符前面输入 \\，可告诉 GitHub 忽略（或规避）Markdown 格式。</p>'
        };
    }
}
</script>

<style scoped>
.sp-p{
    font-size: 24px!important;
    margin-bottom: 16px!important;
    color: #57606a;
}
.sp-content{
    width: 1182px;
    margin: auto;
}
</style>