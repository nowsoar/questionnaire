module.exports = {
    devServer: {
        disableHostCheck:true,
        port: 81,
        proxy: {
            '/api': {
                target: 'http://localhost:8081',
                changeOrigin: true,
            },
            '/register':{
                target: 'http://localhost:8081',
                changeOrigin: true,
            }

        },
    },
};
