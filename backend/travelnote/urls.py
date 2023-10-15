from rest_framework.routers import DefaultRouter
from . import views
from django.urls import path, include

router = DefaultRouter()
router.register('posts', views.PostViewSet)
router.register(r"posts/(?P<post_pk>\d+)/comments", views.CommentViewSet)
router.register('userpost', views.UserPostViewSet)

urlpatterns = [
    path('api/', include(router.urls)),
]