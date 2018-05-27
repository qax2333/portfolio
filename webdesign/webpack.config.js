var path = require('path');
var webpack = require('webpack');
var ExtractTextPlugin = require('extract-text-webpack-plugin');

module.exports = {
    entry: [
        './src/ts/main.ts',
        './src/scss/main.scss'
    ],
    devtool: 'inline-source-map',
    output: {
        path: path.resolve('../server/src/main/resources/static'),
        filename: 'bundle.js'
    },
    module: {
        rules: [
            {
                test: /\.(sass|scss)$/,
                use: ExtractTextPlugin.extract(['css-loader?url=false', 'sass-loader'])
            },
            {
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/
            }
        ]
    },
    resolve: {
        extensions: ['.tsx', '.ts', '.js']
    },
    plugins: [
        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery',
            Tether: 'tether'
        }),
        new ExtractTextPlugin({
            filename: 'bundle.css',
            allChunks: true
        })
    ]
};
