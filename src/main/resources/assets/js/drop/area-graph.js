function AreaGraph() {
    this.draw = function(svgId, data) {
        var graphWidth = 950;
        var graphHeight = 500;
        //var nData = d3.map(function(x) { return Math.sqrt(x ^ 2); });

        var x = d3.scale.linear()
            .domain([0, data.length-1])
            .range([0, graphWidth]);

        var y = d3.scale.linear()
            .range([graphHeight, 0])
            .domain([0, d3.max(data)]);

        var area = d3.svg.area()
            .x(function(d, i) { return x(i); })
            .y0(graphHeight)
            .y1(function(d) { return y(d); });

        var chart = d3.select("#" + svgId)
            .attr("width", graphWidth)
            .attr("height", graphHeight)
            .append("g");

        chart.append("path")
            .datum(data)
            .attr("class", "area")
            .attr("d", area)
            .style("fill", "steelblue");
    };
}