from django.contrib import admin
from .models import Post, Comment, Tag

# Register your models here.
@admin.register(Post)
class PostAmin(admin.ModelAdmin):
    pass

@admin.register(Comment)
class CommentAmin(admin.ModelAdmin):
    pass

@admin.register(Tag)
class TagAmin(admin.ModelAdmin):
    pass