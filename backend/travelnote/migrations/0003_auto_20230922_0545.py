# Generated by Django 3.0.14 on 2023-09-21 20:45

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('travelnote', '0002_auto_20230922_0428'),
    ]

    operations = [
        migrations.AlterField(
            model_name='post',
            name='photo',
            field=models.ImageField(upload_to='travelnote/post/%Y/%m/%d'),
        ),
    ]
