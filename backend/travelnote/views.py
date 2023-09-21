from django.shortcuts import render
from .models import Post
from rest_framework.viewsets import ModelViewSet
from .serializers import PostSerializer
from rest_framework.permissions import AllowAny
from django.db.models import Q

# Create your views here.
class PostViewSet(ModelViewSet):
    queryset = Post.objects.all().select_related("author").prefetch_related("tag_set", "like_user_set")
    serializer_class = PostSerializer
    #permission_classes = [AllowAny] # FIXME: 인증 적용
    
    # def get_queryset(self):
    #     qs = super().get_queryset()
    #     qs = qs.filter(
    #         Q(author = self.request.user) 
    #         | Q(author__in = self.request.user.following_set.all())
    #     )
    #     return qs

    def perform_create(self, serializer):
        serializer.save(author=self.request.user)
        return super().perform_create(serializer)