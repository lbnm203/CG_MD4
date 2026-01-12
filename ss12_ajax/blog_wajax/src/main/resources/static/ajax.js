$(document).ready(function () {
    let currentPage = 0;
    const pageSize = 6;
    let isLoading = false;

    // Xử lý form tìm kiếm
    $('#searchForm').submit(function (event) {
        event.preventDefault();

        const keyword = $('input[name="keyword"]').val();
        const categoryId = $('select[name="categoryId"]').val();

        $.ajax({
            url: '/blogs/search-ajax',
            method: 'GET',
            data: {
                keyword: keyword,
                categoryId: categoryId,
                page: 0,
                size: 6
            },
            success: function (response) {
                $('#blogListContainer').html(response);
                
                currentPage = 0;
                
                // Đếm số blog trong kết quả search
                const searchResultCount = $(response).filter('.col-md-6, .col-lg-4').length;
                
                if (searchResultCount >= pageSize) {
                    $('#loadMoreBtn').show();
                } else {
                    $('#loadMoreBtn').hide();
                }
            },
            error: function (error) {
                console.error('Lỗi khi tìm kiếm:', error);
                $('#blogListContainer').html(`
                    <div class="col-12">
                        <div class="alert alert-danger text-center">
                            <i class="bi bi-exclamation-triangle"></i>
                            Có lỗi xảy ra khi tìm kiếm. Vui lòng thử lại!
                        </div>
                    </div>
                `);
            }
        })
    });

    // Xử lý nút Tải thêm
    $('#loadMoreBtn').click(function () {
        if (isLoading) {
            return;
        }

        isLoading = true;
        currentPage++;

        $.ajax({
            url: '/blogs/load-more',
            method: 'GET',
            data: {
                page: currentPage,
                size: pageSize
            },
            success: function (response) {
                if (response && response.trim().length > 0) {
                    $('#blogListContainer').append(response);
                    
                    // Đếm số blog mới được thêm
                    const newBlogsCount = $(response).filter('.col-md-6, .col-lg-4').length;
                    
                    if (newBlogsCount < pageSize) {
                        $('#loadMoreBtn').hide();
                    }
                } else {
                    $('#loadMoreBtn').hide();
                }
                isLoading = false;
            },
            error: function (error) {
                console.error('Lỗi khi tải thêm:', error);
                isLoading = false;
                $('#blogListContainer').html(`
                    <div class="col-12">
                        <div class="alert alert-danger text-center">
                            <i class="bi bi-exclamation-triangle"></i>
                           Đã hết dữ liệu tải thêm
                        </div>
                    </div>
                `);
            }
        });
    });
});