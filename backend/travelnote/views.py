from django.shortcuts import render
from .models import Post
from rest_framework.viewsets import ModelViewSet
from .serializers import PostSerializer
from rest_framework.permissions import AllowAny
from django.db.models import Q

# Create your views here.
class PostViewSet(ModelViewSet):
    queryset = Post.objects.all()
    serializer_class = PostSerializer
    #permission_classes = [AllowAny] # FIXME: 인증 적용
    
    def get_queryset(self):
        qs = super().get_queryset()
        qs = qs.filter(
            Q(author = self.request.user) 
            | Q(author__in = self.request.user.following_set.all())
        )